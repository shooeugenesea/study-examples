package guava;

import com.google.common.collect.ComparisonChain;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class ComparisonChainTest {

    @Test
    public void test() {
        NumbersObject n1 = new NumbersObject(1,2,3);
        NumbersObject n2 = new NumbersObject(1,2,4);
        NumbersObject n3 = new NumbersObject(1,3,4);
        NumbersObject n4 = new NumbersObject(2,2,4);

        List<NumbersObject> list = Arrays.asList(n4, n3, n2, n1);
        Collections.sort(list, (o1, o2) -> ComparisonChain.start()
                .compare(o1.a,o2.a)
                .compare(o1.b,o2.b)
                .compare(o1.c,o2.c)
                .result());
        Assert.assertTrue(n1 == list.get(0));
        Assert.assertTrue(n2 == list.get(1));
        Assert.assertTrue(n3 == list.get(2));
        Assert.assertTrue(n4 == list.get(3));
    }

}
