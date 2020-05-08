package KafkaAlpakka;

import akka.actor.ActorSystem;
import akka.actor.NotInfluenceReceiveTimeout;
import akka.kafka.ConsumerSettings;
import akka.kafka.ProducerSettings;
import akka.kafka.Subscriptions;
import akka.kafka.javadsl.Consumer;
import akka.kafka.javadsl.Producer;
import akka.kafka.javadsl.Producer$;
import akka.stream.ActorMaterializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaToKafka {

    public static void main(String args[]){

        final ActorSystem system = ActorSystem.create("sample-actor-system");
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final ConsumerSettings<String, String> consumerSettings =
                ConsumerSettings.create(system, new StringDeserializer(), new StringDeserializer())
                        .withBootstrapServers("localhost:9092")
                        .withGroupId("group-id")
                        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        final ProducerSettings<String, String> producerSettings =
                ProducerSettings.create(system, new StringSerializer(), new StringSerializer())
                        .withBootstrapServers("localhost:9092");

        Consumer.plainSource(consumerSettings, Subscriptions.topics("InputTopic"))
                .map(record -> new ProducerRecord<String, String>("OutputTopic", record.value()))
                .runWith(Producer.plainSink(producerSettings), materializer);
    }
}
