package guava;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class OrderingTest {

    @Test
    public void usingToString() {
        NumbersObject n1 = new NumbersObject(1, 2, 3);
        NumbersObject n2 = new NumbersObject(1, 2, 4);
        NumbersObject n3 = new NumbersObject(-1, 2, -3);
        NumbersObject n4 = new NumbersObject(-2, 3, 5);
        List<NumbersObject> numbers = Arrays.asList(n1, n2, n3, n4);
        Collections.sort(numbers, Ordering.usingToString());
        Assert.assertEquals(n3, numbers.get(0));
        Assert.assertEquals(n4, numbers.get(1));
        Assert.assertEquals(n1, numbers.get(2));
        Assert.assertEquals(n2, numbers.get(3));
    }

    @Test
    public void reverse() {
        NumbersObject n1 = new NumbersObject(1, 2, 3);
        NumbersObject n2 = new NumbersObject(1, 2, 4);
        NumbersObject n3 = new NumbersObject(-1, 2, -3);
        NumbersObject n4 = new NumbersObject(-2, 3, 5);
        List<NumbersObject> numbers = Arrays.asList(n1, n2, n3, n4);
        Collections.reverse(numbers);
        Assert.assertEquals(n4, numbers.get(0));
        Assert.assertEquals(n3, numbers.get(1));
        Assert.assertEquals(n2, numbers.get(2));
        Assert.assertEquals(n1, numbers.get(3));
    }

    @Test
    public void nullsFirst_NotComparable() {
        NumbersObject n1 = new NumbersObject(1, 2, 3);
        NumbersObject n2 = new NumbersObject(1, 2, 4);
        NumbersObject n3 = new NumbersObject(-1, 2, -3);
        NumbersObject n4 = new NumbersObject(-2, 3, 5);
        NumbersObject nullNumber = new NumbersObject(null,null,null);
        List<NumbersObject> numbers = Arrays.asList(n1, n2, n3, n4, nullNumber);
        Ordering ordering = Ordering.natural().nullsFirst().onResultOf(new Function<NumbersObject, Integer>() {
            @Override
            public Integer apply(NumbersObject numbersObject) {
                return numbersObject.a;
            }
        });
        Collections.sort(numbers, ordering);
        Assert.assertEquals(nullNumber, numbers.get(0));
        Assert.assertEquals(n4, numbers.get(1));
        Assert.assertEquals(n3, numbers.get(2));
        Assert.assertEquals(n1, numbers.get(3));
        Assert.assertEquals(n2, numbers.get(4));
    }

    @Test
    public void nullsFirst_Comparable() {
        List<Integer> list = Arrays.asList(5, 2, 6, 7, 3, null);
        Collections.sort(list, Ordering.natural().nullsFirst());
        Assert.assertEquals((Integer) null, list.get(0));
        Assert.assertEquals((Integer) 2, list.get(1));
        Assert.assertEquals((Integer) 3, list.get(2));
        Assert.assertEquals((Integer) 5, list.get(3));
        Assert.assertEquals((Integer) 6, list.get(4));
        Assert.assertEquals((Integer) 7, list.get(5));
    }

    @Test
    public void nullsLast_Comparable() {
        List<Integer> list = Arrays.asList(5, 2, 6, 7, 3, null);
        Collections.sort(list, Ordering.natural().nullsLast());
        Assert.assertEquals((Integer) 2, list.get(0));
        Assert.assertEquals((Integer) 3, list.get(1));
        Assert.assertEquals((Integer) 5, list.get(2));
        Assert.assertEquals((Integer) 6, list.get(3));
        Assert.assertEquals((Integer) 7, list.get(4));
        Assert.assertEquals((Integer) null, list.get(5));
    }

    @Test
    public void nullsLast_NotComparable() {
        NumbersObject n1 = new NumbersObject(1, 2, 3);
        NumbersObject n2 = new NumbersObject(1, 2, 4);
        NumbersObject n3 = new NumbersObject(-1, 2, -3);
        NumbersObject n4 = new NumbersObject(-2, 3, 5);
        NumbersObject nullNumber = new NumbersObject(null,null,null);
        List<NumbersObject> numbers = Arrays.asList(n1, n2, n3, n4, nullNumber);
        Ordering ordering = Ordering.natural().nullsLast().onResultOf(new Function<NumbersObject, Integer>() {
            @Override
            public Integer apply(NumbersObject numbersObject) {
                return numbersObject.a;
            }
        });
        Collections.sort(numbers, ordering);
        Assert.assertEquals(n4, numbers.get(0));
        Assert.assertEquals(n3, numbers.get(1));
        Assert.assertEquals(n1, numbers.get(2));
        Assert.assertEquals(n2, numbers.get(3));
        Assert.assertEquals(nullNumber, numbers.get(4));
    }

    @Test
    public void compound() {
        NumbersObject n1 = new NumbersObject(1, 2, 3);
        NumbersObject n2 = new NumbersObject(1, 2, 4);
        NumbersObject n3 = new NumbersObject(-1, 2, -3);
        NumbersObject n4 = new NumbersObject(-2, 3, 5);
        NumbersObject nullNumber = new NumbersObject(null,null,null);
        List<NumbersObject> numbers = Arrays.asList(n1, n2, n3, n4, nullNumber);
        Comparator<NumbersObject> byNullFirstA = Ordering.natural().nullsFirst().onResultOf((t) -> t.a);
        Comparator<NumbersObject> byNullFirstB = Ordering.natural().nullsFirst().onResultOf((t) -> t.b);
        Comparator<NumbersObject> byNullFirstC = Ordering.natural().nullsFirst().onResultOf((t) -> t.c);
        Ordering orderByABC = Ordering.compound(Arrays.asList(byNullFirstA, byNullFirstB, byNullFirstC));
        Collections.sort(numbers, orderByABC);
        Assert.assertEquals(nullNumber, numbers.get(0));
        Assert.assertEquals(n4, numbers.get(1));
        Assert.assertEquals(n3, numbers.get(2));
        Assert.assertEquals(n1, numbers.get(3));
        Assert.assertEquals(n2, numbers.get(4));
    }

    private final class NumbersObject {

        public final Integer a;
        public final Integer b;
        public final Integer c;

        public NumbersObject(Integer a, Integer b, Integer c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("a",a)
                    .add("b",b)
                    .add("c",c)
                    .toString();
        }
    }
}
