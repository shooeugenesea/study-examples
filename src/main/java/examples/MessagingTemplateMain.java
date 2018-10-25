package examples;

import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public class MessagingTemplateMain {

    public static void main(String[] params) {
        QueueChannel channel = new QueueChannel();
        MessagingTemplate template = new MessagingTemplate(channel);
        new Thread(() -> {
            Message<?> message = channel.receive();
            System.out.println("receive:" + message);
            MessageChannel c = (MessageChannel) message.getHeaders().getReplyChannel();
            c.send(MessageBuilder.withPayload("OK!").build());
        }).start();
        template.setReceiveTimeout(10000);
        System.out.println("send and receive:" + template.sendAndReceive(MessageBuilder.withPayload("test").build()));

    }

}
