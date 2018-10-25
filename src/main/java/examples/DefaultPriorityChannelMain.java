package examples;

import org.springframework.integration.channel.PriorityChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

public class DefaultPriorityChannelMain {

    public static void main(String[] params) throws InterruptedException {
        PriorityChannel channel = new PriorityChannel();
        channel.send(MessageBuilder.withPayload("MSG1").setPriority(1).build());
        channel.send(MessageBuilder.withPayload("MSG2").setPriority(2).build());
        channel.send(MessageBuilder.withPayload("MSG3").setPriority(3).build());
        System.out.println(channel.receive()); // priority:3
        System.out.println(channel.receive()); // priority:2
        System.out.println(channel.receive()); // priority:1
    }

}
