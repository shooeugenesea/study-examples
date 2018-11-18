package examples;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;

import javax.jms.MapMessage;
import java.util.UUID;

public class IntegrateActiveMQMain {

    public static void main(String[] params) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:IntegrateActiveMQMain.xml");
        JmsTemplate jmsTemplate = ctx.getBean("jmsTemplate", JmsTemplate.class);
        jmsTemplate.send(session -> {
            MapMessage msg = session.createMapMessage();
            msg.setString("text", UUID.randomUUID().toString());
            return msg;
        });

        PollableChannel output = ctx.getBean("output", PollableChannel.class);
        Message<?> reply = output.receive();
        System.out.println("received: " + reply.getPayload());

        ctx.close();
    }

}
