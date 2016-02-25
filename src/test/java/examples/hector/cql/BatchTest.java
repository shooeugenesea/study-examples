package examples.hector.cql;

import org.junit.Test;

public class BatchTest {

    @Test
    public void test() {
        Batch b = new Batch();
        b.command(new Insert("testinsert1").keyValue("k", "v"));
        b.command(new Insert("testinsert2").keyValue("k", "v"));
        System.out.println(b.toCQL());
    }
    
}
