package examples.windows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class NetStat {

    private final String protocol;
    private final String localAddress;
    private final String externalAddress;
    private final String status;
    private final String pid;
    
    public NetStat(String protocol, String localAddress, String externalAddress, String status, String pid) {
        this.protocol = protocol;
        this.localAddress = localAddress;
        this.externalAddress = externalAddress;
        this.status = status;
        this.pid = pid;
    }
    
    @Override
    public String toString() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("protocol", protocol);
        map.put("localAddress", localAddress);
        map.put("externalAddress", externalAddress);
        map.put("status", status);
        map.put("pid", pid);
        return map.toString();
    }
    
    public String getExternalAddress() {
        return externalAddress;
    }
    
    public String getLocalAddress() {
        return localAddress;
    }
    
    public String getPid() {
        return pid;
    }
    
    public String getProtocol() {
        return protocol;
    }
    
    public String getStatus() {
        return status;
    }
    
    /**
     *   協定       本機位址                       外部位址                   狀態                              PID
     *   TCP 0.0.0.0:135 0.0.0.0:0  LISTENING  972
     * */
    public NetStat(String[] winNetStatResult) {
        this(winNetStatResult[0],winNetStatResult[1],winNetStatResult[2],winNetStatResult[3],winNetStatResult[4]);
    }
    
    public static NetStat port(int port) {
        try {
            NetStat result = null;
            Process p = new ProcessBuilder("cmd.exe", "/C", "netstat -a -n -o | findstr :" + port).start();
            readErrorStream(p);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] a = line.trim().split("\\s+");
                NetStat tmp = new NetStat(a);
                if ("LISTENING".equals(tmp.getStatus())) {
                    result = tmp;
                }
            }
            return result;
        } catch (IOException e) {
            return null;
        }
    }
    

    private static void readErrorStream(final Process process) {
        new Thread(){
            public void run() {
                try (InputStream in = process.getErrorStream()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    
}
