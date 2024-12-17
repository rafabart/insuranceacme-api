package br.com.insuranceacme.insuranceacme_api.service.impl;

import br.com.insuranceacme.insuranceacme_api.domain.document.InsuranceQuoteDocument;
import br.com.insuranceacme.insuranceacme_api.domain.event.InsuranceQuoteEvent;
import br.com.insuranceacme.insuranceacme_api.domain.request.InsuranceQuoteRequest;
import br.com.insuranceacme.insuranceacme_api.domain.response.InsuranceQuoteResponse;
import br.com.insuranceacme.insuranceacme_api.exception.ObjectNotFoundException;
import br.com.insuranceacme.insuranceacme_api.mapper.InsuranceQuoteMapper;
import br.com.insuranceacme.insuranceacme_api.repository.InsuranceQuoteRepository;
import br.com.insuranceacme.insuranceacme_api.service.InsuranceQuoteService;
import br.com.insuranceacme.insuranceacme_api.service.InsuranceValidationService;
import br.com.insuranceacme.insuranceacme_api.stream.InsuranceQuoteProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class InsuranceQuoteServiceImpl implements InsuranceQuoteService {

    private final InsuranceQuoteRepository repository;
    private final InsuranceValidationService validationService;
    private final InsuranceQuoteProducer producer;
    private final InsuranceQuoteMapper mapper;


    @Override
    @Transactional
    public Long create(final InsuranceQuoteRequest request) {
        log.info("InsuranceQuoteServiceImpl.create= {}", request);

        validationService.validate(request);

        final InsuranceQuoteDocument document = mapper.to(request);
        final InsuranceQuoteEvent event = mapper.toEvent(repository.save(document));
        producer.send(event);

        return document.getId();
    }

    @Override
    @Transactional
    public void update(final InsuranceQuoteEvent event) {
        log.info("InsuranceQuoteServiceImpl.update= {}", event);

        findById(event.getId());
        final InsuranceQuoteDocument document = mapper.to(event);
        repository.save(document);
    }

    @Override
    public InsuranceQuoteResponse findById(final Long insuranceId) {
        log.info("InsuranceQuoteServiceImpl.findById= {}", insuranceId);

        final InsuranceQuoteDocument document = repository.findById(insuranceId).orElseThrow(() -> new ObjectNotFoundException("Cotação"));
        return mapper.to(document);
    }

    @Override
    public Page<InsuranceQuoteResponse> findAll(final Pageable pageable) {
        log.info("InsuranceQuoteServiceImpl.findAll");

        final Page<InsuranceQuoteDocument> document = repository.findAll(pageable);
        return document.map(mapper::to);
    }
}
