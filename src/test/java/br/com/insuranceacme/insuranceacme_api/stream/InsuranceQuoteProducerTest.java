package br.com.insuranceacme.insuranceacme_api.stream;

import br.com.insuranceacme.insuranceacme_api.domain.InsuranceQuoteEventMock;
import br.com.insuranceacme.insuranceacme_api.domain.event.InsuranceQuoteEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.Map;

import static org.apache.kafka.server.config.ConfigType.TOPIC;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(
        topics = "insurance-quote-received",
        partitions = 1,
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InsuranceQuoteProducerTest {

    @Autowired
    private EmbeddedKafkaBroker broker;

    @Autowired
    private InsuranceQuoteProducer producer;

    @Test
    void sendMessageWithSuccess() {
        Consumer<String, InsuranceQuoteEvent> consumer = createConsumer(InsuranceQuoteEvent.class);
        this.broker.consumeFromEmbeddedTopics(consumer, "insurance-quote-received");
        final InsuranceQuoteEvent event = InsuranceQuoteEventMock.build();


        producer.send(event);

        ConsumerRecords<String, InsuranceQuoteEvent> records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(5));
        assertThat(records)
                .hasSize(1)
                .allMatch(msg -> msg.value().equals(event));

    }

    private <V> Consumer<String, V> createConsumer(Class<V> classType) {

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(TOPIC, "true", this.broker);
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        DefaultKafkaConsumerFactory<String, V> consumerFactory = new DefaultKafkaConsumerFactory<>(
                consumerProps, new StringDeserializer(), new JsonDeserializer<>()
        );

        return consumerFactory.createConsumer();
    }
}
