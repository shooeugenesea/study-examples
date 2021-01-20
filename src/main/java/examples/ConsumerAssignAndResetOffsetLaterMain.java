package examples;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class ConsumerAssignAndResetOffsetLaterMain {

    public static final String TOPIC = "mytopic3";
    public static int MSG_COUNT = 100;

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        produceMessages();
        int total = 0;
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaConsumerProps("localhost:9092"))) {
            consumer.assign(consumer.partitionsFor(TOPIC).stream().map(p -> new TopicPartition(TOPIC, p.partition())).collect(Collectors.toList()));

            resetOffset(consumer); // will NOT trigger java.lang.IllegalStateException: No current assignment for partition
            total += consumeAllMessages(consumer);
            System.out.println("receive total " + total + " messages");

            resetOffset(consumer);
            total += consumeAllMessages(consumer);
            System.out.println("receive total " + total + " messages");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private static Properties kafkaConsumerProps(String url) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "TestGroup5");
        return properties;
    }

    private static void resetOffset(KafkaConsumer<String, String> consumer) {
        consumer.partitionsFor(TOPIC).forEach(p -> {
            System.out.println("Seek offset 0 in partition " + p.partition());
            consumer.seek(new TopicPartition(TOPIC, p.partition()), 0);
        });
    }

    private static int consumeAllMessages(KafkaConsumer<String, String> consumer) {
        int total = 0;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
            if (records.count() > 0) {
                total += records.count();
                System.out.println("receive:" + records.count() + " messages.");
            } else {
                System.out.println("receive no message");
                break;
            }
        }
        return total;
    }

    public static void produceMessages() throws InterruptedException, ExecutionException, TimeoutException {
        Properties kafkaProps = kafkaProducerProps("localhost:9092");
        createTopicIfNotExist(kafkaProps, TOPIC);
        sendMessages(kafkaProps, TOPIC);
    }

    private static void sendMessages(Properties kafkaProps, String topic) {
        for (int i = 0; i < MSG_COUNT; i++) {
            try (KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProps)) {
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, "mykey", "myval" + i);
                System.out.println("offset:" + producer.send(record).get(10, TimeUnit.SECONDS).offset());
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void createTopicIfNotExist(Properties kafkaProps, String topic) throws InterruptedException, ExecutionException, TimeoutException {
        try (AdminClient adminClient = AdminClient.create(kafkaProps)) {
            NewTopic newTopic = new NewTopic(topic, 100, (short)1);
            if ( adminClient.listTopics().names().get(10, TimeUnit.SECONDS).contains(topic) ) {
                System.out.println("Topic " + topic + " already exist");
                return;
            }
            System.out.println("Topic " + topic + " doesn't exist, create it");
            List<NewTopic> newTopics = new ArrayList<>();
            newTopics.add(newTopic);
            adminClient.createTopics(newTopics).all().get(10, TimeUnit.SECONDS);
        }
    }

    private static Properties kafkaProducerProps(String url) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

}
