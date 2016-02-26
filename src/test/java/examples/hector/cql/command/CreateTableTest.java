package examples.hector.cql.command;

import org.junit.Assert;
import org.junit.Test;

import examples.hector.cql.command.CreateTable;

public class CreateTableTest {

    @Test
    public void test() {
        Assert.assertEquals(
                "CREATE TABLE test (key varchar PRIMARY KEY,gender varchar);", 
                new CreateTable("test").pk("key", "varchar").keyTypes("gender", "varchar").toCQL());
    }
    
}
