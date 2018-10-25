package examples;

import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.channel.RendezvousChannel;
import org.springframework.integration.support.MessageBuilder;

public class RendezvousChannelMain {

    public static void main(String[] params) {
        RendezvousChannel channel = new RendezvousChannel();
        receive(channel);
        System.out.println("send 1");
        channel.send(MessageBuilder.withPayload("MSG1").build());
        System.out.println("send 2 and block until message was received");
        channel.send(MessageBuilder.withPayload("MSG2").build());
        System.out.println("won't reach here");
    }

    private static void receive(QueueChannel channel) {
        new Thread(() -> System.out.println("receive:" + channel.receive())).start();
    }

}
