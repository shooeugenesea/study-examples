package examples.concurrent;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static examples.concurrent.ForkJoinPoolErrorMain.ErrorAction.MAIN_TASK_ID;


/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class ForkJoinPoolErrorMain {

    static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] params) throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(2);
        ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
        s.scheduleAtFixedRate(() -> System.out.println(pool), 0, 1, TimeUnit.SECONDS);
        pool.submit(new ErrorAction(MAIN_TASK_ID));
        latch.await(10, TimeUnit.SECONDS);
        pool.shutdown();
        s.shutdown();
    }

    static class ErrorAction extends RecursiveAction {

        public static final int MAIN_TASK_ID = -1;
        private final int id;

        ErrorAction(int id) {
            this.id = id;
        }

        private boolean isSubTask() {
            return id >= 0;
        }

        @Override
        protected void compute() {
            if (isSubTask()) {
                if (id % 2 == 1) {
                    throw new IllegalStateException("Error when id is odd number");
                }
            } else {
                List<ErrorAction> actions = IntStream.range(0,10).mapToObj(idx -> new ErrorAction(idx)).collect(Collectors.toList());
                actions.forEach(ErrorAction::fork);
                actions.forEach(action -> {
                    try {
                        action.join();
                    } catch (Exception ex) {
                        System.err.println(ex + ", id=" + action.id);
                    }
                });
                latch.countDown();
            }
            System.out.println("compute done. id=" + id);
        }
    }

}
