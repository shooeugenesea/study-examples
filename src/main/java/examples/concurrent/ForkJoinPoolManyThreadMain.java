package examples.concurrent;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ForkJoinPoolManyThreadMain {

    static final CountDownLatch latch = new CountDownLatch(1);
    private static final AtomicInteger threadCount = new AtomicInteger();

    public static void main(String[] params) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(2);
        ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
        s.scheduleAtFixedRate(() -> {
            System.out.println(pool); }, 0, 1, TimeUnit.SECONDS);
        pool.execute(new MainAction());
        latch.await();
        s.shutdown();
        pool.shutdown();
    }


    private static class MainAction extends RecursiveAction {

        @Override
        protected void compute() {
            List<SubAction> actions = IntStream.range(0,100).mapToObj(idx -> new SubAction()).collect(toList());
            actions.forEach(SubAction::fork);
            actions.forEach(SubAction::join);
            latch.countDown();
        }
    }

    private static class SubAction extends RecursiveAction {

        @Override
        protected void compute() {
            try {
                System.out.println(Thread.currentThread().getName() + " sleep 1 seconds");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
