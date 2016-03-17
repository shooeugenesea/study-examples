package examples.lambda;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    @Test
    public void num() {
        Assert.assertEquals(9, Operator.PLUS.num(6, 3));
        Assert.assertEquals(3, Operator.MINUS.num(6, 3));
        Assert.assertEquals(18, Operator.MULTIPLY.num(6, 3));
        Assert.assertEquals(2, Operator.DIVIDE.num(6, 3));
    }
    
}
