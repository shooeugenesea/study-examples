package examples.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class ForkJoinSingleThreadMain {

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] params) throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(1);
        pool.submit(new MainTask());
        latch.await();
    }

    private static class MainTask extends RecursiveAction {

        @Override
        protected void compute() {
            Subtask subtask1 = new Subtask(1);
            Subtask subtask2 = new Subtask(2);
            System.out.println(Thread.currentThread().getName() + " fork subtask1");
            subtask1.fork();
            System.out.println(Thread.currentThread().getName() + " fork subtask2");
            subtask2.fork();
            System.out.println(Thread.currentThread().getName() + " join subtask1");
            subtask1.join();
            System.out.println(Thread.currentThread().getName() + " join subtask2");
            subtask2.join();
            System.out.println(Thread.currentThread().getName() + " join done");
            latch.countDown();
        }
    }

    private static class Subtask extends RecursiveAction {

        private final int id;

        Subtask(int id) {
            this.id = id;
        }

        @Override
        protected void compute() {
            System.out.println(Thread.currentThread().getName() + ":" + id + " compute");
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + ":" + id + " compute done");
        }
    }


}
