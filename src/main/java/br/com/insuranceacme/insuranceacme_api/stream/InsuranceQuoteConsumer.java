package br.com.insuranceacme.insuranceacme_api.stream;

import br.com.insuranceacme.insuranceacme_api.domain.event.InsuranceQuoteEvent;
import br.com.insuranceacme.insuranceacme_api.service.InsuranceQuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InsuranceQuoteConsumer {

    final private InsuranceQuoteService insuranceQuoteService;


    @RetryableTopic(
            dltTopicSuffix = "-dead-letter-topic",
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            retryTopicSuffix = "-retry-letter-topic",
            attempts = "3",
            backoff = @Backoff(delay = 1200, multiplier = 2.0, maxDelay = 240000),
            autoCreateTopics = "yes")
    @KafkaListener(topics = "insurance-policy-created", groupId = "insurance-quote")
    public void consume(InsuranceQuoteEvent event) {
        try {
            log.info("InsuranceQuoteConsumer.received= {}", event);
            insuranceQuoteService.update(event);
        } catch (Exception e) {
            throw e;
        }

    }
}
