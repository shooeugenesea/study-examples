package examples;

import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PriorityChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.Comparator;

public class DirectChannelMain {

    public static void main(String[] params) {
        DirectChannel channel = new DirectChannel();
        channel.subscribe(new MyMessageHandler(1));
        channel.subscribe(new MyMessageHandler(2));
        channel.subscribe(new MyMessageHandler(3));
        channel.send(MessageBuilder.withPayload("MSG1").build());
        channel.send(MessageBuilder.withPayload("MSG2").build());
        channel.send(MessageBuilder.withPayload("MSG3").build());
    }

    static class MyMessageHandler implements MessageHandler {

        private final int id;

        MyMessageHandler(int id) {
            this.id = id;
        }

        @Override
        public void handleMessage(Message<?> message) throws MessagingException {
            System.out.println("handler " + id + " receive:" + message);
        }
    }


}
