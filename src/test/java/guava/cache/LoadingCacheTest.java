/*
 * Copyright (C) 2016 Digital River, Inc. All Rights Reserved
 *
 */
package guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:iliao@digitalriver.com">Isaac Liao</a>
 */
public class LoadingCacheTest {

    private int expireInSeconds = 5;
    private int maxSize = 3;

    private LoadingCache<String, String> stringLoadingCache = CacheBuilder.newBuilder()
            .expireAfterWrite(expireInSeconds, TimeUnit.SECONDS)
            .maximumSize(maxSize)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    String uuid = UUID.randomUUID().toString();
                    System.out.println("get new string:" + uuid + " for " + s);
                    return uuid;
                }
            });

    @Test
    public void expireAfterWrite() throws ExecutionException, InterruptedException {
        String string1 = stringLoadingCache.get("A");
        Assert.assertEquals(string1, stringLoadingCache.get("A"));
        TimeUnit.SECONDS.sleep(6);
        String string2 = stringLoadingCache.get("A");
        Assert.assertNotEquals(string1, string2);
        Assert.assertEquals(string2, stringLoadingCache.get("A"));
    }

    @Test
    public void maximumSize() throws ExecutionException {
        String string1ByA = stringLoadingCache.get("A");
        String string1ByB = stringLoadingCache.get("B");
        String string1ByC = stringLoadingCache.get("C");
        Assert.assertEquals(string1ByA, stringLoadingCache.get("A"));
        Assert.assertEquals(string1ByB, stringLoadingCache.get("B"));
        Assert.assertEquals(string1ByC, stringLoadingCache.get("C"));
        String string1ByD = stringLoadingCache.get("D");
        Assert.assertEquals(string1ByD, stringLoadingCache.get("D"));
        String string2ByA = stringLoadingCache.get("A");
        Assert.assertNotEquals(string2ByA, string1ByA);
    }

}
