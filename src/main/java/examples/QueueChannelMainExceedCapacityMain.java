package examples;

import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;

public class QueueChannelMainExceedCapacityMain {

    public static void main(String[] params) {
        QueueChannel channel = new QueueChannel(2);
        send(channel, "1");
        send(channel, "2");
        send(channel, "3");
        System.out.println(channel.receive(1000));
        System.out.println(channel.receive(1000));
        System.out.println(channel.receive(1000));
    }

    private static void send(QueueChannel channel, String s) {
        try {
            System.out.println("send:" + s);
            channel.send(MessageBuilder.withPayload(s).build(), 1000); // the third message won't be sent
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
