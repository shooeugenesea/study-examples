package examples.transformer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

import java.util.HashMap;
import java.util.Map;

public class PayloadSerializingTransformerMain {

    public static void main(String[] params) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:PayloadSerializingTransformerMain.xml");
        MessageChannel input = ctx.getBean("input", MessageChannel.class);
        PollableChannel output = ctx.getBean("output", PollableChannel.class);
        TextMessage message = new TextMessage("testa", "1");

        input.send(MessageBuilder.withPayload(message).build());
        System.out.println(output.receive().getPayload()); // localId will become null
    }

}
