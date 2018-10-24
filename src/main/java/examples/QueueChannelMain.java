package examples;

import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

public class QueueChannelMain {

    static final Map<Object, AtomicInteger> receiveMsgCount = new ConcurrentHashMap<>();
    static final CountDownLatch l = new CountDownLatch(10);

    public static void main(String[] params) throws InterruptedException {
        QueueChannel channel = new QueueChannel();
        new Receiver(channel).start();
        new Receiver(channel).start();
        new Receiver(channel).start();
        LongStream.range(0,l.getCount()).forEach(idx -> {
            String msg = "Message" + idx;
            System.out.println("send msg:" + msg);
            channel.send(MessageBuilder.withPayload(msg).build());
        });
        l.await();
        System.out.println("test done. receiveMsgCount:" + receiveMsgCount);
    }

    public static class Receiver extends Thread {
        private QueueChannel channel;

        Receiver(QueueChannel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            while (true) {
                Message m = channel.receive();
                Object pl = m.getPayload();
                receiveMsgCount.putIfAbsent(pl, new AtomicInteger());
                receiveMsgCount.get(pl).incrementAndGet();
                System.out.println(Thread.currentThread().getName() + " got message: " + pl);
                l.countDown();
            }
        }
    }

}
