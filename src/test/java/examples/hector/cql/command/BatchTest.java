package examples.hector.cql.command;

import org.junit.Test;

import examples.hector.cql.command.Batch;
import examples.hector.cql.command.Insert;

public class BatchTest {

    @Test
    public void test() {
        Batch b = new Batch();
        b.command(new Insert("testinsert1").keyValue("k", "v"));
        b.command(new Insert("testinsert2").keyValue("k", "v"));
        System.out.println(b.toCQL());
    }
    
}
