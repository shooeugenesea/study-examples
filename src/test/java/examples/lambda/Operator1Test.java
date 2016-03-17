package examples.lambda;

import org.junit.Assert;
import org.junit.Test;

public class Operator1Test {

    @Test
    public void num() {
        Assert.assertEquals(9, Operator1.PLUS.num(6, 3));
        Assert.assertEquals(3, Operator1.MINUS.num(6, 3));
        Assert.assertEquals(18, Operator1.MULTIPLY.num(6, 3));
        Assert.assertEquals(2, Operator1.DIVIDE.num(6, 3));
    }
 
}
