package examples.lambda;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;


public class SortTest {

    @Test
    public void jdkSort() {
        Assert.assertEquals(Arrays.toString(new String[]{"1","22","333"}), Arrays.toString(Sort.jdk7Sort("333","22","1")));
    }
 
    @Test
    public void lambda1Sort() {
        Assert.assertEquals(Arrays.toString(new String[]{"1","22","333"}), Arrays.toString(Sort.lambdaSort1("333","22","1")));
    }
    
    @Test
    public void lambda2Sort() {
        Assert.assertEquals(Arrays.toString(new String[]{"1","22","333"}), Arrays.toString(Sort.lambdaSort2("333","22","1")));
    }
    
}
