package examples.tls;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Paths;

/**
 * Reference http://www.programgo.com/article/71463377839/
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class ClientWithoutAuth {

    private static String CLIENT_KEY_STORE = Paths.get("src/main/resources/certs/catrust.jks").toAbsolutePath().toString();

    public static void main(String[] params) throws Exception {
        System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);
        SocketFactory sf = SSLSocketFactory.getDefault();
        Socket s = sf.createSocket("localhost", 8443);
        PrintWriter writer = new PrintWriter(s.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        writer.println("hello\n");
        writer.flush();
        System.out.println("client receive:" + reader.readLine());
        s.close();
    }

}
