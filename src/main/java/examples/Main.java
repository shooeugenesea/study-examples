package examples;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Main {

    private static final ExecutorService e = Executors.newCachedThreadPool();
    private static final int sendCnt = 1000;
    private static final CountDownLatch receiveCnt = new CountDownLatch(sendCnt);

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        String topic = "mytopic";
        Properties kafkaProps = kafkaProps("localhost:9092");
        createTopicIfNotExist(kafkaProps, topic);
        keepSendingMessage(kafkaProps, topic);
        keepConsumingMessage(kafkaProps, topic);
        receiveCnt.await();
        System.out.println("shutdown");
        e.shutdownNow();
    }

    private static void keepConsumingMessage(Properties kafkaProps, String topic) {
        e.execute(() -> {
            try (KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(kafkaProps)) {
                consumer.subscribe(Arrays.asList(topic));
                ConsumerRecords<String, String> records = null;
                while (true) {
                    records = consumer.poll(Duration.ofSeconds(10));
                    if (records.count() > 0) {
                        IntStream.range(0, records.count()).forEach(i -> receiveCnt.countDown());
                        System.out.println("receive:" + records.count() + " messages");
                    }
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void keepSendingMessage(Properties kafkaProps, String topic) {
        e.execute(() -> {
            int count = 0;
            while (count++ < sendCnt) {
                try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProps)) {
                    ProducerRecord<String, String> record = new ProducerRecord<>(topic, "mykey", "myval" + count);
                    System.out.println("offset:" + producer.send(record).get(10, TimeUnit.SECONDS).offset());
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private static Properties kafkaProps(String url) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "TestGroup");

        return properties;
    }

    private static void createTopicIfNotExist(Properties kafkaProps, String topic) throws InterruptedException, ExecutionException, TimeoutException {
        AdminClient adminClient = AdminClient.create(kafkaProps);
        NewTopic newTopic = new NewTopic(topic, 1, /* replicationFactor */ (short)1);
        if ( adminClient.listTopics().names().get(10, TimeUnit.SECONDS).contains(topic) ) {
            System.out.println("Topic " + topic + " already exist");
            return;
        }
        System.out.println("Topic " + topic + " doesn't exist, create it");
        List<NewTopic> newTopics = new ArrayList<>();
        newTopics.add(newTopic);
        adminClient.createTopics(newTopics).all().get(10, TimeUnit.SECONDS);
        adminClient.close();
    }

}
