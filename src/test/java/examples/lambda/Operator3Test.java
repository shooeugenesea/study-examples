package examples.lambda;

import org.junit.Assert;
import org.junit.Test;

import examples.lambda.Operator3.Calculator;
import examples.lambda.Operator3.MyInteger;

public class Operator3Test {

    @Test
    public void num() {
        MyInteger[] numbers = new MyInteger[]{
                new MyInteger(6),
                new MyInteger(3)
        };
        Assert.assertEquals(9, Calculator.all(numbers, MyInteger::plus).intValue());
        Assert.assertEquals(3, Calculator.all(numbers, MyInteger::minus).intValue());
        Assert.assertEquals(18, Calculator.all(numbers, MyInteger::multiply).intValue());
        Assert.assertEquals(2, Calculator.all(numbers, MyInteger::divide).intValue());
        
        
    }
 
}
