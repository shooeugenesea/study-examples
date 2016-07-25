package guava;

import com.google.common.collect.ImmutableMultiset;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class ImmutableMultisetTest {

    @Test
    public void count() {
        ImmutableMultiset<String> set = ImmutableMultiset.of("A","B","A");
        Assert.assertEquals(2,set.count("A"));
        Assert.assertEquals(1,set.count("B"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutable() {
        ImmutableMultiset.of("A","B").add("Q");
    }

}
