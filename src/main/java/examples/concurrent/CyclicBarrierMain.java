package examples.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class CyclicBarrierMain {

    public static void main(String[] params) throws InterruptedException {
        int runner = 5;
        System.out.println("my name is " + Thread.currentThread().getName());
        CountDownLatch gameOver = new CountDownLatch(runner);
        CyclicBarrier b = new CyclicBarrier(runner, () -> {
            System.out.println(Thread.currentThread().getName() + ": barrier done");
        });
        IntStream.range(0,runner).forEach(idx -> {
            new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " wait " + idx);
                        b.await();
                        System.out.println("count down " + idx);
                        gameOver.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        });
        System.out.println("wait for gameover");
        gameOver.await();
        System.out.println("game over");
    }

}
