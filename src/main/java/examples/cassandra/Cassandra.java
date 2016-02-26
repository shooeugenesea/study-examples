package examples.cassandra;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import examples.windows.NetStat;
import examples.windows.TaskKill;

public class Cassandra implements Closeable {

    private String executablePath;
    private Process process;
    
    public Cassandra(String executablePath) {
        this.executablePath = Paths.get(executablePath).toFile().getAbsolutePath();
    }
    
    public void start() throws IOException {
        System.out.println("start cassandra. path: " + executablePath);
        this.process = startProcess(executablePath);        
    }
    
    public void stop() {
        if (process != null) {
            process.destroy();
        }
        NetStat netstat = NetStat.port(9160);
        if (netstat != null) {
            TaskKill.pid(netstat.getPid());
        }
    }
    
    private static Process startProcess(String... commands) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);
        final Process process = builder.start();
        // process will hang when start process without read console and error input stream
        readConsoleInputStream(process);
        readErrorStream(process);
        return process;
    }

    private static void readConsoleInputStream(final Process process) {
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
    
    private static void readErrorStream(final Process process) {
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

    @Override
    public void close() throws IOException {
        stop();
    }
    
}
