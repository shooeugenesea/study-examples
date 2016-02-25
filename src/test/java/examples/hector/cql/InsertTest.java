package examples.hector.cql;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

public class InsertTest {

    @Test
    public void test() {
        String key = UUID.randomUUID().toString();
        Assert.assertEquals(
                "INSERT INTO cfName ('k','測試1' ) VALUES ('" + key + "','測試1' );", 
                new Insert("cfName").keyValue("k", key).keyValue("測試1","測試1").toCQL());
    }
    
}
