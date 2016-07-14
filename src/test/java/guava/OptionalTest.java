package guava;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class OptionalTest {


    @Test
    public void of() {
        try {
            Optional.of(null);
            Assert.fail("Optional.of shouldn't accept null");
        } catch (NullPointerException ex) {
        }
        Optional opt = Optional.of("Test");
        Assert.assertTrue(opt.isPresent());
        Assert.assertEquals("Test", opt.get());
        Assert.assertEquals("Test", opt.orElse("QQ"));
    }


}
