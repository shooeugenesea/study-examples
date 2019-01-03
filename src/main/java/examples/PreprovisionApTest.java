package examples;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.codahale.metrics.MetricRegistry.name;

public class PreprovisionApTest {

    private static MetricRegistry metrics = new MetricRegistry();
    private final static Timer preprovisionAPTimer = metrics.timer(name(PreprovisionApTest.class, "PreprovisionAP"));

    public static void main(String[] params) throws IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException, InterruptedException {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, (certificate, authType) -> true).build();

        CloseableHttpClient httpclient = HttpClients.custom().setSSLContext(sslContext)
                .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        CookieStore cookieStore = new BasicCookieStore();
        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

        startReport();
        login(httpclient, httpContext);
        IntStream.range(0, 4000).forEach(idx -> {
            try {
                String domainId = createDomain(httpclient, httpContext);
                String zoneId = createZone(domainId, httpclient, httpContext);
                for ( int i = 0; i < 5; i++ ) {
                    createWlan(zoneId, httpclient, httpContext);
                }
                preprovisionAPs(1, zoneId, httpclient, httpContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        httpclient.close();
        System.out.println("DONE");
        TimeUnit.MINUTES.sleep(5);
    }

    private static void preprovisionAPs(int apCount, String zoneId, CloseableHttpClient httpclient, HttpContext httpContext) throws IOException, InterruptedException {
        for ( int i = 0; i < apCount; i++ ) {
            preprovisionAp(zoneId, httpclient, httpContext);
        }
    }

    static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(5, TimeUnit.SECONDS);
    }

    private static String randomMACAddress(){
        Random rand = new Random();
        byte[] macAddr = new byte[6];
        rand.nextBytes(macAddr);
        macAddr[0] = (byte)(macAddr[0] & (byte)254);  //zeroing last 2 bytes to make it unicast and locally adminstrated
        StringBuilder sb = new StringBuilder(18);
        for(byte b : macAddr){

            if(sb.length() > 0)
                sb.append(":");

            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static void preprovisionAp(String zoneId, CloseableHttpClient httpclient, HttpContext httpContext) throws IOException, InterruptedException {
        HttpPost post = new HttpPost("https://10.206.84.130:8443/wsg/api/public/v8_1/aps/deep");
        String serial = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        String mac = randomMACAddress();
        String body = "{\n" +
                "    \"mac\": \"" + mac + "\",\n" +
                "    \"zoneId\": \"" + zoneId + "\",\n" +
                "    \"serial\": \"" + serial + "\",\n" +
                "    \"model\": \"R310\",\n" +
                "    \"name\": \"RuckusAP\"\n" +
                "}";
        post.setEntity(new StringEntity(body));
        Timer.Context context = preprovisionAPTimer.time();
        CloseableHttpResponse response = httpclient.execute(post, httpContext);
        context.stop();
        try {
            HttpEntity entity2 = response.getEntity();
            if (response.getStatusLine().getStatusCode() != 201) {
                System.out.println("Preprovision AP fail?.. " + response.getStatusLine());
                System.exit(1);
            }
            System.out.println(IOUtils.toString(entity2.getContent(), "UTF-8"));
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);
        } finally {
            response.close();
        }
    }

    private static String createWlan(String zoneId, CloseableHttpClient httpclient, HttpContext httpContext) throws IOException {
        HttpPost post = new HttpPost("https://10.206.84.130:8443/wsg/api/public/v8_0/rkszones/" + zoneId + "/wlans");
        String wlanId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        String body = "{\n" +
                "    \"name\": \"" + wlanId + "\",\n" +
                "    \"ssid\": \"" + wlanId + "\"\n" +
                "}";
        post.setEntity(new StringEntity(body));
        CloseableHttpResponse response = httpclient.execute(post, httpContext);
        try {
            String responseBody = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            HttpEntity entity2 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);
            if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
                return new Gson().fromJson(responseBody, JsonObject.class).get("id").getAsString();
            } else {
                System.out.println("Create wlan fail?:" + wlanId + ".. " + response.getStatusLine());
                return StringUtils.EMPTY;
            }
        } finally {
            response.close();
        }
    }

    private static String createZone(String domainId, CloseableHttpClient httpclient, HttpContext httpContext) throws IOException {
        HttpPost post = new HttpPost("https://10.206.84.130:8443/wsg/api/public/v8_0/rkszones");
        String zone = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        String body = "{\n" +
                "    \"domainId\": \"" + domainId + "\",\n" +
                "    \"name\": \"" + zone + "\",\n" +
                "    \"login\": {\n" +
                "        \"apLoginName\": \"admin\",\n" +
                "        \"apLoginPassword\": \"admin!234\"\n" +
                "    }\n" +
                "}";
        post.setEntity(new StringEntity(body));
        CloseableHttpResponse response = httpclient.execute(post, httpContext);
        try {
            String responseBody = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            HttpEntity entity2 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);
            if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {
                return new Gson().fromJson(responseBody, JsonObject.class).get("id").getAsString();
            } else {
                System.out.println("Create zone fail?:" + zone + ".. " + response.getStatusLine());
                return StringUtils.EMPTY;
            }
        } finally {
            response.close();
        }
    }

    private static String createDomain(CloseableHttpClient httpclient, HttpContext httpContext) throws IOException {
        HttpPost post = new HttpPost("https://10.206.84.130:8443/wsg/api/public/v8_0/domains");
        String domain = UUID.randomUUID().toString().replaceAll("-", "").substring(4, 20);
        String body = "{\n" +
                "    \"name\": \"" + domain + "\"\n" +
                "}";
        post.setEntity(new StringEntity(body));
        CloseableHttpResponse response = httpclient.execute(post, httpContext);
        try {
            String responseBody = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            HttpEntity entity2 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);
            if (response.getStatusLine().getStatusCode() == 200
                    || response.getStatusLine().getStatusCode() == 201) {
                return new Gson().fromJson(responseBody, JsonObject.class).get("id").getAsString();
            } else {
                System.out.println("Create domain fail? :" + domain + ".. " + response.getStatusLine());
                return StringUtils.EMPTY;
            }
        } finally {
            response.close();
        }
    }

    private static void login(CloseableHttpClient httpclient, HttpContext httpContext) throws IOException {
        HttpPost loginPost = new HttpPost("https://10.206.84.130:8443/wsg/api/public/v8_1/session");
        loginPost.setEntity(new StringEntity("{\n" +
                "  \"username\": \"admin\",\n" +
                "  \"password\": \"admin!234\"\n" +
                "}"));
        CloseableHttpResponse loginResponse = httpclient.execute(loginPost, httpContext);
        try {
            System.out.println(loginResponse.getStatusLine());
            HttpEntity entity2 = loginResponse.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);
            System.out.println("Login successfull.");
        } finally {
            loginResponse.close();
        }
    }


}
