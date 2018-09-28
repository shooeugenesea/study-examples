package examples;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        context.start();


        MessageChannel input = context.getBean("input", MessageChannel.class );
        PollableChannel output = context.getBean("output", PollableChannel.class );

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            input.send(MessageBuilder.withPayload(scanner.nextLine()).build());
        }

    }

}
