package guava;

import com.google.common.collect.ComparisonChain;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class OrderingTest {

    @Test
    public void natural() {
        List<NumbersObject> numbers = Arrays.asList(
                new NumbersObject(1,2,3),
                new NumbersObject(1,2,4),
                new NumbersObject(-1,2,-3),
                new NumbersObject(-2,3,5)
        );
        System.out.println(numbers);
        Collections.sort(numbers, );
    }

}
