package examples.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SemaphoreMain {

    public static void main(String[] params) throws InterruptedException {
        TicketPool pool = new TicketPool(30000);
        int sellPoint = 100;
        CountDownLatch readyToSell = new CountDownLatch(sellPoint);
        IntStream.range(0, sellPoint)
                .forEach(idx -> new Thread(){
                    @Override
                    public void run() {
                        try {
                            TicketSellPoint point = new TicketSellPoint(pool);
                            readyToSell.countDown();
                            System.out.println("SellPoint " + idx + " is ready to sell. Wait others...");
                            readyToSell.await();
                            System.out.println("SellPoint " + idx + " start to sell");
                            point.sellUntilNotAvailable();
                            System.out.println("SellPoint " + idx + " close");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start());
        while (pool.isAvailable()) {
            System.out.println("[Main] Ticket is still available: " + pool.availableTickets());
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("Total Buy count:" + totalBuyTickets);
        System.out.println("Total refund count:" + totalRefundTickets);
    }

    private static class TicketSellPoint {
        private final TicketPool pool;
        TicketSellPoint(TicketPool pool) {
            this.pool = pool;
        }
        public void sellUntilNotAvailable() {
            while (pool.isAvailable()) {
                new TicketGrabber().grab(pool);
            }
            System.out.println("pool is not available, stop selling");
        }
    }

    private static class TicketGrabber {
        public void grab(TicketPool pool) {
            if (!pool.isAvailable()) {
                return;
            }
            Random r = new Random();
            int maxBuy = 4; // Rule: Every one can buy max 4 tickets
            int buyNumber = r.nextInt(maxBuy);
            if (buyNumber == 0) {
                return;
            }
            if (pool.buy(buyNumber)) {
                System.out.println("Buy " + buyNumber + " available:" + pool.availableTickets());
            }

            boolean refound = r.nextBoolean();
            int refundNumber = r.nextInt(buyNumber);
            if (refound) {
                System.out.println("Refund " + refundNumber + " available:" + pool.availableTickets());
                pool.refund(refundNumber);
            }
            if (refundNumber > buyNumber) {
                System.err.println("BUG!!!" + refundNumber + " > " + buyNumber);
                System.exit(1);
            }
        }
    }

    private static final AtomicInteger totalRefundTickets = new AtomicInteger();
    private static final AtomicInteger totalBuyTickets = new AtomicInteger();

    private static class TicketPool {
        private final Semaphore s;

        TicketPool(int total) {
            s = new Semaphore(total);
        }

        public boolean buy(int ticketNumber) {
            totalBuyTickets.addAndGet(ticketNumber);
            return s.tryAcquire(ticketNumber);
        }

        public void refund(int ticketNumber) {
            totalRefundTickets.addAndGet(ticketNumber);
            s.release(ticketNumber);
        }

        public boolean isAvailable() {
            return s.availablePermits() > 0;
        }

        public int availableTickets() {
            return s.availablePermits();
        }
    }

}
