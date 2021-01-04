package examples;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        Properties kafkaProps = kafkaProps("dev:9092");
        createTopicIfNotExist(kafkaProps, "mytopic");


//        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProps);
//
//        ProducerRecord<String, String> record = new ProducerRecord<>("mytopic", "myval");
//        try {
//            producer.send(record);
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//        }

    }

    private static Properties kafkaProps(String url) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", url);
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());
        return properties;
    }

    private static void createTopicIfNotExist(Properties kafkaProps, String topic) throws InterruptedException, ExecutionException, TimeoutException {
        AdminClient adminClient = AdminClient.create(kafkaProps);
        NewTopic newTopic = new NewTopic("topicName", 1, /* replicationFactor */ (short)1);
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
