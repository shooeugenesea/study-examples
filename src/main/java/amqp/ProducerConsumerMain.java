package amqp;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class ProducerConsumerMain {

    private final static AtomicInteger sentMsg = new AtomicInteger();
    private final static AtomicInteger receiveMsg = new AtomicInteger();
    private final static String MSG_5K;
    private final static String QUEUE_NAME = "hello";

    static {
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < 5000; i++ ) {
            sb.append(String.valueOf(i));
        }
        MSG_5K = sb.toString();
    }

    public static void main(String[] params) throws IOException, TimeoutException {
        new Thread(){
            @Override
            public void run() {
                try {
                    ConnectionFactory factory = new ConnectionFactory();
                    factory.setHost("localhost");
                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();

                    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

                    Consumer consumer = new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope,
                                                   AMQP.BasicProperties properties, byte[] body)
                                throws IOException {
                            String message = new String(body, "UTF-8");

                            receiveMsg.incrementAndGet();
//                            System.out.println(" [x] Received message '" + message.length() + "'");
                        }
                    };
                    channel.basicConsume(QUEUE_NAME, true, consumer);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable(){

            @Override
            public void run() {
                System.out.println("receiveMsg:" + receiveMsg.getAndSet(0) + ", sentMsg:" + sentMsg.getAndSet(0));
            }
        }, 0, 1, TimeUnit.SECONDS);

        send();
    }

    private static void send() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message = MSG_5K;
            for ( ;; ) {
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                sentMsg.incrementAndGet();
//            System.out.println(" [x] Sent '" + message + "'");
            }

//            channel.close();
//            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
