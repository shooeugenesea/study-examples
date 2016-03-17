package examples.lambda;

import org.junit.Assert;
import org.junit.Test;

public class Operator2Test {

    @Test
    public void num() {
        Assert.assertEquals(9, Operator2.PLUS.num(6, 3));
        Assert.assertEquals(3, Operator2.MINUS.num(6, 3));
        Assert.assertEquals(18, Operator2.MULTIPLY.num(6, 3));
        Assert.assertEquals(2, Operator2.DIVIDE.num(6, 3));
    }
 
}
