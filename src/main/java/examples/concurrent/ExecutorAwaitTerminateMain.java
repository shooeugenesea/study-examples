package examples.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorAwaitTerminateMain {

    public static void main(String[] params) throws ExecutionException, InterruptedException {
        ExecutorService e = Executors.newCachedThreadPool();
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(e);
        List<Future<String>> futures = new ArrayList<>();
        futures.add(completionService.submit(new MyCallable("1")));
        futures.add(completionService.submit(new MyCallable("2")));
        futures.add(completionService.submit(new MyCallable("3")));
        futures.add(completionService.submit(new MyCallable("4")));
        System.out.println(completionService.take().get() + " done");
        futures.forEach(future -> future.cancel(true));
        e.shutdown();
    }

    private static class MyCallable implements Callable<String> {
        private final String name;
        public MyCallable(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            try {
                int sleepMs = Math.abs(new Random().nextInt(10000));
                long start = System.currentTimeMillis();

                while (true) {
                    long runMs = System.currentTimeMillis() - start;
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException(name + " plane to run for " + sleepMs +
                                                            " ms, but is interrupted at " + runMs + " ms");
                    }
                    if (runMs > sleepMs) {
                        break;
                    }
                }
                System.out.println(name + " run for " + sleepMs + " ms");
                return name;
            } catch (Exception ex) {
                System.err.println(name + " err:" + ex);
                throw ex;
            }
        }
    }

}
