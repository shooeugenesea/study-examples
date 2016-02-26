package examples.hector.cql.command;

import org.junit.Assert;
import org.junit.Test;

public class BatchTest {

    @Test
    public void test() {
        Batch b = new Batch();
        b.command(new Insert("testinsert1").keyValue("k", "v"));
        b.command(new Insert("testinsert2").keyValue("k", "v"));
        System.out.println(b.toCQL());
        Assert.assertEquals("BEGIN BATCH INSERT INTO testinsert1 ('k' ) VALUES ('v' );INSERT INTO testinsert2 ('k' ) VALUES ('v' ); APPLY BATCH;", b.toCQL());
    }
    
}
