package examples.cassandra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

import examples.windows.NetStat;
import examples.windows.TaskKill;

public class Cassandra {

    private Path executablePath;
    private Process process;
    
    public Cassandra(Path executablePath) {
        this.executablePath = executablePath;
    }
    
    public void start() throws IOException {
        System.out.println("start cassandra. path: " + executablePath);
        this.process = startProcess(executablePath.toString());
        System.out.println("cassandra started");
    }

    public void stop() {
        System.out.println("stop cassandra...");
        if (process != null) {
            process.destroy();
        }
        NetStat netstat = NetStat.port(9160);
        if (netstat != null) {
            TaskKill.pid(netstat.getPid());
        }
        System.out.println("cassandra stopped");
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
