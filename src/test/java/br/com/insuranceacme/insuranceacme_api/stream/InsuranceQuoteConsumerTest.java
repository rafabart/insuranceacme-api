package br.com.insuranceacme.insuranceacme_api.stream;

import br.com.insuranceacme.insuranceacme_api.domain.InsuranceQuoteEventMock;
import br.com.insuranceacme.insuranceacme_api.domain.event.InsuranceQuoteEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Duration;
import java.util.Map;

import static org.apache.kafka.server.config.ConfigType.TOPIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(
        topics = {"insurance-policy-created", "insurance-policy-created-dead-letter-topic-0"},
        partitions = 1,
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InsuranceQuoteConsumerTest {

    @Autowired
    private EmbeddedKafkaBroker broker;

    @Autowired
    private KafkaTemplate<String, InsuranceQuoteEvent> producer;


    @MockitoSpyBean
    private InsuranceQuoteConsumer messageConsumer;

    @Test
    void receiveMessageWithSuccess() {
        Consumer<String, InsuranceQuoteEvent> consumer = createConsumer(InsuranceQuoteEvent.class);
        this.broker.consumeFromEmbeddedTopics(consumer, "insurance-policy-created");
        final InsuranceQuoteEvent event = InsuranceQuoteEventMock.build();

        producer.send(new ProducerRecord<>("insurance-policy-created", event));

        ConsumerRecords<String, InsuranceQuoteEvent> records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(5));
        assertThat(records)
                .hasSize(1)
                .allMatch(msg -> msg.value().equals(event));

    }

    @Test
    void receiveMessageRetries() throws InterruptedException {
        final InsuranceQuoteEvent event = InsuranceQuoteEventMock.build();

        producer.send(new ProducerRecord<>("insurance-policy-created", event));
        assertThrows(RuntimeException.class,
                () -> messageConsumer.consume(event));
        Thread.sleep(10000);

        verify(messageConsumer, atLeast(3)).consume(any(
                InsuranceQuoteEvent.class));

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
