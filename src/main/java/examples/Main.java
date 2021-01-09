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

public class Main {

    private static final ExecutorService e = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        String topic = "mytopic";
        Properties kafkaProps = kafkaProps("localhost:9092");
        createTopicIfNotExist(kafkaProps, topic);
        keepSendingMessage(kafkaProps, topic);
        keepConsumingMessage(kafkaProps, topic);
    }

    private static void keepConsumingMessage(Properties kafkaProps, String topic) {
        e.execute(() -> {
            try (KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(kafkaProps)) {
                consumer.subscribe(Arrays.asList(topic));
                ConsumerRecords<String, String> records = null;
                while (true) {
                    records = consumer.poll(Duration.ofSeconds(10));
                    if (records.count() > 0) {
                        System.out.println("receive:" + toString(records));
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
            while (count++ < 10000) {
                try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProps)) {
                    ProducerRecord<String, String> record = new ProducerRecord<>(topic, "mykey", "myval" + count);
                    System.out.println("offset:" + producer.send(record).get(10, TimeUnit.SECONDS).offset());
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    public static void main2(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        String topic = "mytopic";
        Properties kafkaProps = kafkaProps("localhost:9092");
        createTopicIfNotExist(kafkaProps, topic);

        try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProps);
             KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(kafkaProps);
        ) {
            consumer.subscribe(Arrays.asList(topic));
            ProducerRecord<String, String> record = new ProducerRecord<>("mytopic", "mykey", "myval");
            System.out.println("offset:" + producer.send(record).get(10, TimeUnit.SECONDS).offset());
            int retry = 0;
            ConsumerRecords<String, String> records = null;
            while (retry++ < 10) {
                records = consumer.poll(Duration.ofSeconds(10));
                if (records.count() > 0) {
                    break;
                }
            }
            System.out.println("receive:" + toString(records));
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private static String toString(ConsumerRecords<String, String> records) {
        System.out.println("count:" + records.count());
        StringBuilder sb = new StringBuilder();
        for (ConsumerRecord<String, String> record : records) {
            sb.append(record.toString());
        }
        return sb.toString();
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
