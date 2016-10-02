package examples.tls;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.security.KeyStore;

/**
 * Reference http://www.programgo.com/article/71463377839/
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class ServerDontNeedClientAuth {

    private static String SERVER_KEY_STORE = Paths.get("src/main/resources/certs/server.jks").toAbsolutePath().toString();
    private static String SERVER_KEY_STORE_PASSWORD = "123456";

    public static void main(String[] params) throws Exception {
//        System.setProperty("javax.net.debug", "ssl,handshake");
        System.setProperty("javax.net.ssl.trustStore", SERVER_KEY_STORE);
        SSLContext context = SSLContext.getInstance("TLS");
        KeyStore ks = KeyStore.getInstance("jceks");
        ks.load(new FileInputStream(SERVER_KEY_STORE), null);
        KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");
        kf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
        context.init(kf.getKeyManagers(), null, null);

        ServerSocketFactory factory = context.getServerSocketFactory();
        ServerSocket serverSocket = factory.createServerSocket(8443);
        SSLServerSocket sslServerSocket =  (SSLServerSocket) serverSocket;
        sslServerSocket.setNeedClientAuth(false);
        while(true){
            try{
                System.out.println("listen port 8443..");
                Socket socket = sslServerSocket.accept();
                System.out.println("accept:" + socket);
                InputStream is = socket.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
                String readLine = buffer.readLine();
                System.out.println("server receive:" + readLine);
                socket.getOutputStream().write("1234567890".getBytes());
                socket.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
