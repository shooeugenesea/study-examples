package examples.cassandra;

import org.junit.Test;

public class CassandraTestSupportTest {

    @Test
    public void test() throws Exception {
        CassandraTestSupport testee = new CassandraTestSupport();
        testee.before();
        testee.after();
    }
    
}
