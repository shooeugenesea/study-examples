package examples.tls;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Paths;
import java.security.KeyStore;

/**
 * Reference http://www.programgo.com/article/71463377839/
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class ClientWithoutAuth {

    private static String CLIENT_KEY_STORE = Paths.get("src/main/resources/certs/catrust.jks").toAbsolutePath().toString();
    private static String CLIENT_KEY_STORE_PASSWORD = "123456";

    public static void main(String[] params) throws Exception {
        connect();
    }

    private static Socket createNonAuthenticationSocket()throws Exception{
        System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);
        SocketFactory sf = SSLSocketFactory.getDefault();
        Socket s = sf.createSocket("localhost", 8443);
        return s;
    }

    private static Socket createAuthenticationSocket() throws Exception{
        System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);
        SSLContext context = SSLContext.getInstance("TLS");
        KeyStore ks = KeyStore.getInstance("jceks");
        ks.load(new FileInputStream(CLIENT_KEY_STORE), null);
        KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");
        kf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());
        context.init(kf.getKeyManagers(), null, null);

        SocketFactory factory = context.getSocketFactory();
        Socket s = factory.createSocket("localhost", 8443);
        return s;
    }

    private static void connect()throws Exception{
        Socket s = createNonAuthenticationSocket();
//     Socket s = createAuthenticationSocket();

        PrintWriter writer = new PrintWriter(s.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        writer.println("hello");
        writer.flush();
        System.out.println(reader.readLine());
        s.close();
    }

}
