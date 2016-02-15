package examples.mbean;

import java.io.IOException;

import javax.management.JMException;

public class MXBeanClient {

    public static void main(String[] params) throws IOException, JMException {
        JMXInvoker invoker = new JMXInvoker(1234);
        invoker.connect();
        System.out.println(invoker.getAttr("example:type=Test", "Name"));
        invoker.setAttr("example:type=Test", "Name", "testname");
        System.out.println(invoker.getAttr("example:type=Test", "Name"));
        invoker.invoke("example:type=Test", "printName", new Object[]{}, new String[]{});
        invoker.disconnect();
    }
    
}
