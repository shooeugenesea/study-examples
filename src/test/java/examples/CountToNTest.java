package examples;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class CountToNTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {1}, {1}, { Runtime.getRuntime().availableProcessors() }, { 10 }, { 100 }
        });
    }

    private final int testThreadPoolSize;

    public CountToNTest(int testThreadPoolSize) {
        this.testThreadPoolSize = testThreadPoolSize;
        this.executorService = Executors.newCachedThreadPool();
    }

    private ExecutorService executorService;

    @After
    public void before() {
        executorService.shutdown();
    }

    @Test
    public void test() throws InterruptedException {
        int n = 100000;
        CountToN testee = new CountToN(executorService, n);
        System.out.println("testThreadPoolSize:" + testThreadPoolSize + ", spent time:" + testee.go());
        Assert.assertEquals(n, testee.getCurrentCount());
    }

}
