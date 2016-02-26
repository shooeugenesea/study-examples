package examples.windows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TaskKill {

    public static void pid(String pid) {
        try {
            startProcess("cmd.exe", "/C", "TASKKILL /F /PID " + pid + " /T");
        } catch (Throwable ex) {
            ex.printStackTrace();
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
