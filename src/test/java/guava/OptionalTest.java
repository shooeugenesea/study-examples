package guava;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;

public class OptionalTest {


    @Test
    public void test() {
        try {
            Optional.of(null);
            Assert.fail("Optional.of shouldn't accept null");
        } catch (NullPointerException ex) {
        }
        Optional opt = Optional.of("Test");
        Assert.assertTrue(opt.isPresent());
        Assert.assertEquals("Test", opt.get());
        Assert.assertEquals("Test", opt.or("QQ"));
        Assert.assertEquals("Test", opt.orNull());
        Assert.assertEquals(ImmutableSet.of("Test"), opt.asSet());

        Optional nullableOptional = Optional.fromNullable(null);
        Assert.assertFalse(nullableOptional.isPresent());
        try {
            Assert.assertNull(nullableOptional.get());
            Assert.fail();
        } catch (IllegalStateException ex) {
        }
        Assert.assertEquals("QQ", nullableOptional.or("QQ"));
        Assert.assertNull(nullableOptional.orNull());
        Assert.assertEquals(ImmutableSet.of(), nullableOptional.asSet());
        Assert.assertEquals(Optional.absent(), nullableOptional);
    }

}
