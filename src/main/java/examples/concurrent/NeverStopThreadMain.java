package examples.concurrent;

import java.util.concurrent.TimeUnit;

public class NeverStopThreadMain {

    private static boolean stop = false;

    public static void main(String[] params) throws InterruptedException {
        Thread t = new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
            }
        });
        t.start();
        TimeUnit.SECONDS.sleep(1);
        stop = true;
    }

}


