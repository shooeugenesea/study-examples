package examples.groovy

import static org.junit.Assert.*

import org.junit.Assert
import org.junit.Test

import examples.Hello

class HelloTest {

    @Test
    public void test() {
        Hello h = new Hello();
        Assert.assertTrue("hello".equals(h.hello()));
    }
}
