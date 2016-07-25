package guava;

import com.google.common.collect.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
@RunWith(Parameterized.class)
public class MultisetTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { HashMultiset.create() },
                {TreeMultiset.create() },
                {LinkedHashMultiset.create() },
                {ConcurrentHashMultiset.create()},
        });
    }

    private final Multiset<String> testee;

    public MultisetTest(Multiset<String> testee) {
        this.testee = testee;
    }

    @Test
    public void test() {
        testee.addAll(Arrays.asList("A","B","A"));
        Assert.assertEquals(2, testee.count("A"));
        Assert.assertEquals(1, testee.count("B"));
    }

}
