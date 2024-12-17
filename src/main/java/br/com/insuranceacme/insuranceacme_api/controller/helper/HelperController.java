package br.com.insuranceacme.insuranceacme_api.controller.helper;

import br.com.insuranceacme.insuranceacme_api.domain.event.InsuranceQuoteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/helper")
public class HelperController {


    private final KafkaTemplate<String, InsuranceQuoteEvent> kafkaTemplate;

    @PostMapping("/kafka/insurance-policy-created")
    public void create(@RequestBody final InsuranceQuoteEvent event) {

        kafkaTemplate.send("insurance-policy-created", event.getId().toString(), event);

    }
}
