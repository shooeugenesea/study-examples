package examples.elasticsearch;

import org.junit.Test;

public class ElasticsearchTestSupportTest {

    @Test
    public void test() throws Exception {
        ElasticsearchTestSupport testee = new ElasticsearchTestSupport();
        testee.before();
        testee.after();
    }
    
}
