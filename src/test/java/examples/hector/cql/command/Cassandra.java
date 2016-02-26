package examples.hector.cql.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Cassandra {

    private Process process;
    
    private Cassandra(String executablePath) throws IOException {
        String path = Paths.get(executablePath, "cassandra.bat").toFile().getAbsolutePath();
        System.out.println("execute: " + path);
        this.process = startProcess(path);
    }
    
    public static Cassandra start() throws IOException {
        return new Cassandra("apache-cassandra-1.2.19");
    }
 
    public void stop() {
        if (process != null) {
            process.destroy();
        }
    }
    
    public static void main(String[] params) throws IOException {
        Cassandra.start();
        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
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
    
}
