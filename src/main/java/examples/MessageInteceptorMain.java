package examples;

import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;

public class MessageInteceptorMain {

    public static void main(String[] params) {
        QueueChannel channel = new QueueChannel();
        channel.addInterceptor(new ChannelInterceptor() {
            @Override
            public boolean preReceive(MessageChannel channel) {
                System.out.println("pre receive");
                return true;
            }

            @Override
            public Message<?> postReceive(Message<?> message, MessageChannel channel) {
                System.out.println("post receive:" + message);
                return message;
            }

            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                System.out.println("presend:" + message);
                return message;
            }

            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                System.out.println("post sned:" + message);
            }
        });
        channel.send(MessageBuilder.withPayload("test").build());
        System.out.println("receive:" + channel.receive());
    }

}
