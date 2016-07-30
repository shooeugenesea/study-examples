package examples.lambda;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class IterationTest {

    @Test
    public void test() {
        Iteration testee = new Iteration();
        List<String> list = Arrays.asList("ABC","QQQ","ASCSA","DWQDWAS");
        String toString = testee.toString(list,',');
        String forEachToString = testee.forEachToString(list, ',');
        Assert.assertEquals("ABC,QQQ,ASCSA,DWQDWAS", toString);
        Assert.assertEquals("ABC,QQQ,ASCSA,DWQDWAS", forEachToString);
    }

}
