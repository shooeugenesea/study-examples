package examples.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.LongStream;

public class CountDownLatchMain {

    public static void main(String[] params) {
        CountDownLatch latch = new CountDownLatch(new Random().nextInt(10));
        LongStream.range(0, latch.getCount()).forEach(idx -> {
            new Thread(() -> {
                System.out.println(idx + " is ready");
                latch.countDown();
                await(latch);
                System.out.println("bye");
            }).start();
        });
        System.out.println("wait");
        await(latch);
        System.out.println("Bye bye!");
    }

    private static void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
