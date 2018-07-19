package examples;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class CountToN {

    private final Semaphore s;
    private AtomicInteger count = new AtomicInteger();
    private final int n;
    private final ExecutorService executorService;

    public CountToN(ExecutorService executorService, int n) {
        this.executorService = executorService;
        this.n = n;
        this.s = new Semaphore(n);
    }

    /**
     * @return spent time in millis
     */
    public long go() {
        long start = System.currentTimeMillis();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        final CountDownLatch latch = new CountDownLatch(availableProcessors);
        for ( int i = 0; i < availableProcessors; i++ ) {
            executorService.execute(() -> {
                while (s.tryAcquire()) {
                    count.incrementAndGet();
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) { }
        return System.currentTimeMillis() - start;
    }

    public int getCurrentCount() {
        return count.get();
    }

}