package examples.tls;

import javax.net.ServerSocketFactory;
import javax.net.ssl.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.logging.Logger;

/**
 * Reference http://www.programgo.com/article/71463377839/
 * Reference https://docs.oracle.com/cd/E19509-01/820-3503/6nf1il6er/index.html
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class TlsServer {

    private static String SERVER_KEY_STORE = "C:\\Users\\isaac\\workspace_github\\study-practice\\src\\main\\resources\\certs\\server.jks";
    private static String SERVER_KEY_STORE_PASSWORD = "123456";

    public static void main(String[] params) throws Exception {
        SSLServerSocket sslServerSocket= createSSLServerSocket();

        while(true){
            try{
                System.out.println("listen port 8443..");
                Socket socket = sslServerSocket.accept();
                System.out.println("accept:" + socket);
                InputStream is = socket.getInputStream();
                byte[] bytes = new byte[Short.MAX_VALUE];
                int len = -1;

                while((len = is.read(bytes))>0){
                    System.out.println(new String(bytes,0,len));
                    if(len<bytes.length){
                        break;
                    }
                }
                socket.getOutputStream().write("1234567890".getBytes());
                socket.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private static SSLServerSocket createSSLServerSocket() throws Exception{
        // whether enable the debug mode
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
        // set whether need the client authentication
        sslServerSocket.setNeedClientAuth(true);
        return sslServerSocket;
    }

}
