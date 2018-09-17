package examples.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolDeadlockMain {

    private static final CountDownLatch l = new CountDownLatch(1);
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] params) throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(2);
        pool.submit(ForkJoinTask.adapt(new Runnable(){
            @Override
            public void run() {
                Obj1Locker locker1 = new Obj1Locker();
                Obj2Locker locker2 = new Obj2Locker();
                locker1.fork();
                locker2.fork();
                locker1.join();
                locker2.join();
                l.countDown();
            }
        }));
        l.await();
    }

    private static class Obj1Locker extends RecursiveAction {

        @Override
        public void compute() {
            synchronized (lock1) {
                System.out.println("obj1Locker got lock1");
                sleep();
                synchronized (lock2) {
                    System.out.println("obj1Locker got lock2");
                }
            }
        }


    }

    private static class Obj2Locker extends RecursiveAction {

        @Override
        public void compute() {
            synchronized (lock2) {
                System.out.println("obj2Locker got lock2");
                sleep();
                synchronized (lock1) {
                    System.out.println("obj2Locker got lock1");
                }
            }
        }


    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
    }

}
