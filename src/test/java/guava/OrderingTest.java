package guava;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class OrderingTest {

    private final NumbersObject n1 = new NumbersObject(1, 2, 3);
    private final NumbersObject n2 = new NumbersObject(1, 2, 4);
    private final NumbersObject n3 = new NumbersObject(-1, 2, -3);
    private final NumbersObject n4 = new NumbersObject(-2, 3, 5);
    private List<NumbersObject> numbers;

    @Before
    public void before() {
        numbers = Arrays.asList(n1, n2, n3, n4);
    }

    @Test
    public void usingToString() {
        Collections.sort(numbers, Ordering.usingToString());
        Assert.assertEquals(n3, numbers.get(0));
        Assert.assertEquals(n4, numbers.get(1));
        Assert.assertEquals(n1, numbers.get(2));
        Assert.assertEquals(n2, numbers.get(3));
    }

    @Test
    public void reverse() {
        Collections.reverse(numbers);
        Assert.assertEquals(n4, numbers.get(0));
        Assert.assertEquals(n3, numbers.get(1));
        Assert.assertEquals(n2, numbers.get(2));
        Assert.assertEquals(n1, numbers.get(3));
    }

    @Test
    public void nullsFirst() {
        numbers = new ArrayList<>(numbers);
        numbers.add(null);
        Ordering ordering = Ordering.natural().nullsLast();
        Collections.sort(numbers, ordering);
        Assert.assertEquals(null, numbers.get(0));
        Assert.assertEquals(n1, numbers.get(1));
        Assert.assertEquals(n2, numbers.get(2));
        Assert.assertEquals(n3, numbers.get(3));
        Assert.assertEquals(n4, numbers.get(4));
    }

}
