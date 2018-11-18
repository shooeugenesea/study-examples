package examples;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.util.UUID;

public class JmsTemplateMain {

    public static void main(String[] params) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
        JmsTemplate template = new JmsTemplate(factory);
        template.setDefaultDestinationName("test");
        template.send(session -> {
            MapMessage msg = session.createMapMessage();
            msg.setString("text", UUID.randomUUID().toString());
            return msg;
        });
        MapMessage msg = (MapMessage) template.receive();
        System.out.println("receive msg:" + msg.getString("text"));
    }


}
