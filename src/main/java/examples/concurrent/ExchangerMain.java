package examples.concurrent;

import java.util.concurrent.Exchanger;

public class ExchangerMain {

    public static void main(String[] params) throws InterruptedException {
        Exchanger<String> e = new Exchanger<>();
        new Thread(() -> {
            try {
                System.out.println("New Thread get message:" + e.exchange("New"));
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }).start();
        System.out.println("Main thread get message:" + e.exchange("Main"));
    }

}
