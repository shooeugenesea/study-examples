package examples.transformer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

public class MapToObjectTransformerMain {

    public static void main(String[] params) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:MapToObjectTransformerMain.xml");
        MessageChannel input = ctx.getBean("input", MessageChannel.class);
        PollableChannel output = ctx.getBean("output", PollableChannel.class);

        TextMessageEmptyConstructor message = new TextMessageEmptyConstructor("testa");

        input.send(MessageBuilder.withPayload(message).build());
        System.out.println(output.receive().getPayload()); // localId will become null
    }

}
