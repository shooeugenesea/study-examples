/*
 * Copyright (C) 2016 Digital River, Inc. All Rights Reserved
 *
 */
package guava.cache;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:iliao@digitalriver.com">Isaac Liao</a>
 */
public class SupplierTest {

    private Supplier<String> stringCache = Suppliers.memoizeWithExpiration (
            () -> {
                String uuid = UUID.randomUUID().toString();
                System.out.println("get new string:" + uuid);
                return uuid;
            }, 5, TimeUnit.SECONDS);

    @Test
    public void test() throws InterruptedException {
        String string1 = stringCache.get();
        Assert.assertEquals(string1, stringCache.get());
        TimeUnit.SECONDS.sleep(6);
        String string2 = stringCache.get();
        Assert.assertNotEquals(string1, string2);
        Assert.assertEquals(string2, stringCache.get());
    }

}
