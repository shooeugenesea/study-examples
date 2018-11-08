package examples;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.concurrent.TimeUnit;

public class ActiveMQMain {

    public static void main(String[] params) throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
        Connection connection = getConnection(factory);
        connection.start();
        System.out.println("create session");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dst = session.createQueue("test");

        System.out.println("create producer");
        MessageProducer producer = session.createProducer(dst);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        TextMessage msg = session.createTextMessage("my test msg");

        System.out.println("send message");
        producer.send(msg);

        MessageConsumer consumer = session.createConsumer(dst);
        System.out.println("consume message");
        System.out.println(consumer.receive());

        System.out.println("close session");
        session.close();
        System.out.println("close connection");
        connection.close();
        System.out.println("done");
    }

    private static Connection getConnection(ConnectionFactory factory) {
        Connection result = null;
        while (result == null) {
            try {
                result = factory.createConnection();
                System.out.println("connection get!");
            } catch (Exception ex) {
                System.err.println(ex);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static void asyncStart(BrokerService brokerService) {
        new Thread(() -> {
            try {
                brokerService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
