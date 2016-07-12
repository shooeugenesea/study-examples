package examples;

import org.junit.Assert;
import org.junit.Test;

public class CountToNTest_NotPluggableAndTunnable {

    @Test
    public void test() throws InterruptedException {
        int n = 100000;
        CountToN_NotPluggableAndTunnable testee = new CountToN_NotPluggableAndTunnable(n);
        System.out.println("spent time:" + testee.go());
        Assert.assertEquals(n, testee.getCurrentCount());
    }

}
