package examples.elasticsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import examples.OS;
import examples.windows.NetStat;
import examples.windows.TaskKill;
import org.junit.Assert;

public class Elasticsearch {

    public static final int LISTEN_PORT = 9200;

    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private Path executablePath;
    private Process process;
    
    public Elasticsearch(Path executablePath) {
        this.executablePath = executablePath;
    }
    
    public Future<Void> start() throws IOException {
        System.out.println("start elasticsearch. path: " + executablePath);
        this.process = startProcess(executablePath.toString());
        System.out.println("elasticsearch started");
        return executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                String osname = System.getProperty("os.name").toLowerCase();
                while (true) {
                    if (osname.contains("linux")) {
                        try (Socket s = new Socket("127.0.0.1", LISTEN_PORT)) {
                            System.out.println("elasticsearch is started");
                            break;
                        } catch (Throwable ex) {
                            System.out.println(ex);
                        }
                    } else {
                        if (NetStat.port(LISTEN_PORT) != null) {
                            break;
                        }
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
                return null;
            }
        });
    }

    public Future<Void> stop() {
        System.out.println("stop elasticsearch...");
        if (process != null) {
            process.destroy();
        }
        if (!OS.isLinux()) {
            NetStat netstat = NetStat.port(LISTEN_PORT);
            if (netstat != null) {
                TaskKill.pid(netstat.getPid());
            }
        }

        return executor.submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                if (!OS.isLinux()) {
                    while (true) {
                        if (NetStat.port(LISTEN_PORT) == null) {
                            break;
                        }
                        TimeUnit.SECONDS.sleep(1);
                    }
                }
                return null;
            }
        });
    }
    
    private Process startProcess(String... commands) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);
        final Process process = builder.start();
        // process will hang when start process without read console and error input stream
        readConsoleInputStream(process);
        readErrorStream(process);
        return process;
    }

    private void readConsoleInputStream(final Process process) {
        new Thread(){
            public void run() {
                try (InputStream in = process.getInputStream()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    
    private void readErrorStream(final Process process) {
        new Thread(){
            public void run() {
                try (InputStream in = process.getErrorStream()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
