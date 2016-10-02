package examples.tls;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class WebSslClient {

    public static void main(String[] params) throws Exception {
        String httpsURL = "https://www.google.com.tw";
        URL myurl = new URL(httpsURL);
        HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
        InputStream ins = con.getInputStream();
        InputStreamReader isr = new InputStreamReader(ins);
        BufferedReader in = new BufferedReader(isr);

        String inputLine;

        while ((inputLine = in.readLine()) != null)
        {
            System.out.println(inputLine);
        }

        in.close();
    }


}
