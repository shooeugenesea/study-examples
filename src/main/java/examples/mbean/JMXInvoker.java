package examples.mbean;

import java.io.IOException;

import javax.management.Attribute;
import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXInvoker {

    private static final String URL = "service:jmx:rmi:///jndi/rmi://%s:%d/jmxrmi";
    private String jmxHost = "127.0.0.1";
    private Integer jmxPort;

    private JMXConnector connector;
    private MBeanServerConnection mbsc;

    public JMXInvoker(Integer jmxPort) {
        this("127.0.0.1", jmxPort);
    }

    public JMXInvoker(String jmxHost, Integer jmxPort) {
        this.jmxHost = jmxHost;
        this.jmxPort = jmxPort;
    }

    public void connect() throws IOException {
        try {
            String serviceUrl = String.format(URL, jmxHost, jmxPort);
            JMXServiceURL jmxURL = new JMXServiceURL(serviceUrl);
            connector = JMXConnectorFactory.connect(jmxURL);
            mbsc = connector.getMBeanServerConnection();
            return;
        }
        catch (IOException t) {
            throw t;
        }

    }

    public Object invoke(String objectName, String methodName, Object[] arguments, String[] types) throws JMException, IOException {
        ObjectName name = new ObjectName(objectName);
        return mbsc.invoke(name, methodName, arguments, types);
    }

    public Object getAttr(String objectName, String name) throws JMException, IOException {
        return mbsc.getAttribute(new ObjectName(objectName), name);
    }

    public void setAttr(String objectName, String name, String value) throws JMException, IOException {
        mbsc.setAttribute(new ObjectName(objectName), new Attribute(name, value));
    }
    
    public void disconnect() {
        try {
            if (connector != null) {
                connector.close();
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static Object invoke(String objectName, String methodName, int port) throws JMException, IOException {
        return invoke(objectName, methodName, new Object[0], new String[0], port);
    }

    public static Object invoke(String objectName, String methodName, Object[] arguments, String[] types, int port) throws JMException, IOException {
        JMXInvoker invoker = new JMXInvoker(port);
        try {
            invoker.connect();
            return invoker.invoke(objectName, methodName, arguments, types);
        }
        finally {
            invoker.disconnect();
        }
    }

}
