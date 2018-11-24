package examples.router;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

public class RouterMain {

    public static void main(String[] params) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:RouterMain.xml");
        MessageChannel input = ctx.getBean("input", MessageChannel.class);

        input.send(MessageBuilder.withPayload(new MyMessage("database", "needPersist")).build());
        input.send(MessageBuilder.withPayload(new MyMessage("memory", "noNeedPersist")).build());


        PollableChannel dbOutput = ctx.getBean("database", PollableChannel.class);
        System.out.println("from DB:" + dbOutput.receive());
        PollableChannel memOutput = ctx.getBean("memory", PollableChannel.class);
        System.out.println("from Memory" + memOutput.receive());
    }

}
