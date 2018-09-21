package examples.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceWithExceptionMain {

    public static void main(String[] params) {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(() -> {
            throw new IllegalStateException("test throw illegalState");
        });
        ex.shutdown();
    }


}
