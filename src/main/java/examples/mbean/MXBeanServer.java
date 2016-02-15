package examples.mbean;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class MXBeanServer {

    public static void main(String[] params) throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, InterruptedException, UnknownHostException {
        String ipAddress = InetAddress.getLocalHost().getHostAddress();
        System.setProperty("com.sun.management.jmxremote.port=1234", "");
        System.setProperty("com.sun.management.jmxremote.ssl=false", "");
        System.setProperty("com.sun.management.jmxremote.authenticate=false", "");
        System.setProperty("java.rmi.server.hostname=" + ipAddress, "");
        
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
        ObjectName name = new ObjectName("example:type=Test"); 
        TestMXBean mbean = new TestMXBeanImpl(); 
        mbs.registerMBean(mbean, name); 
        System.out.println("start mbean server 10 minutes..." + ipAddress);
        TimeUnit.MINUTES.sleep(10);
    }
    
}
