package examples.curl;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class Curl {

    private final String httpMethod;
    private String url = null;
    private String data = null;

    private Curl(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public static Curl post() {
        return new Curl("POST");
    }

    public static Curl get() {
        return new Curl("GET");
    }

    public static Curl delete() {
        return new Curl("DELETE");
    }

    public Curl url(String url) {
        this.url = url;
        return this;
    }

    public Curl data(String data) {
        this.data = data;
        return this;
    }

    public String sendAndGet() throws Exception {
        StringBuilder s = new StringBuilder();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(data));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            s.append(IOUtils.toString(entity2.getContent(), Charset.defaultCharset()));
            EntityUtils.consume(entity2);
        } finally {
            response2.close();
            httpclient.close();
        }
        return s.substring(0);
    }

    public static void main(String[] params) throws Exception {
        String rsp = Curl.post()
                .url("http://www.google.com")
                .data("{'valid':5}")
        .sendAndGet();
        System.out.println(rsp);
    }

}
