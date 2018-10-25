package examples;

import org.springframework.integration.channel.PriorityChannel;
import org.springframework.integration.support.MessageBuilder;

import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.stream.LongStream;

public class PriorityChannelCustomComparatorMain {

    public static void main(String[] params) {
        PriorityChannel channel = new PriorityChannel(Comparator.comparingLong(m -> (Long) m.getHeaders().get("MYVAL")));
        channel.send(MessageBuilder.withPayload("MSG1").setHeader("MYVAL", 3L).build());
        channel.send(MessageBuilder.withPayload("MSG2").setHeader("MYVAL", 2L).build());
        channel.send(MessageBuilder.withPayload("MSG3").setHeader("MYVAL", 1L).build());
        System.out.println(channel.receive()); // MYVAL:1
        System.out.println(channel.receive()); // MYVAL:2
        System.out.println(channel.receive()); // MYVAL:3
    }

}
