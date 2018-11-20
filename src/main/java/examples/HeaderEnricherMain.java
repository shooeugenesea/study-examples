package examples;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

public class HeaderEnricherMain {

    public static void main(String[] params) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:HeaderEnricherMain.xml");
        MessageChannel input = ctx.getBean("input", MessageChannel.class);
        input.send(MessageBuilder.withPayload("test").build());


        PollableChannel output = ctx.getBean("output", PollableChannel.class);
        System.out.println(output.receive().getHeaders().get("mykey")); // print myval
    }

}
