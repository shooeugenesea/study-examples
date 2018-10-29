package examples.transformer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.*;

import java.util.HashMap;
import java.util.Map;

public class TransformerMain {

    public static void main(String[] params) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:TransformerMain.xml");
        MessageChannel input = ctx.getBean("input", MessageChannel.class);
        PollableChannel output = ctx.getBean("output", PollableChannel.class);
        Map<String,String> message = new HashMap<>();
        message.put("text", "here");
        input.send(MessageBuilder.withPayload(message).build());
        System.out.println(output.receive().getPayload());


        input.send(MessageBuilder.withPayload("fromString").build());
        System.out.println(output.receive().getPayload());
    }

}
