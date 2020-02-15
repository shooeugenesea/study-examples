package examples.elasticsearch;

import examples.curl.Curl;
import org.junit.Test;

import java.io.IOException;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class CreateIndexTest extends ElasticsearchTestSupport {

    public CreateIndexTest() throws IOException {
        super();
    }

    @Test
    public void createIndex() throws Exception {
        System.out.println(Curl.post().url("http://localhost:9200/myindex/type1/1").data("{\"valid\":5}").sendAndGet());
        System.out.println(Curl.post().url("http://localhost:9200/myindex/type2/1").data("{\"valid\":\"40\"}").sendAndGet());
    }

}
