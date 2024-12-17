package br.com.insuranceacme.insuranceacme_api.stream;

import br.com.insuranceacme.insuranceacme_api.domain.event.InsuranceQuoteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InsuranceQuoteProducer {

    private final KafkaTemplate<String, InsuranceQuoteEvent> kafkaTemplate;

    public void send(final InsuranceQuoteEvent event) {

        log.info("InsuranceQuoteProducer.send= {}", event);
        kafkaTemplate.send("insurance-quote-received", event.getId().toString(), event);
    }
}
