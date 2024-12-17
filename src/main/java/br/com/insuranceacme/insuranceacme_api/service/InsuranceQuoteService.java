package br.com.insuranceacme.insuranceacme_api.service;

import br.com.insuranceacme.insuranceacme_api.domain.event.InsuranceQuoteEvent;
import br.com.insuranceacme.insuranceacme_api.domain.request.InsuranceQuoteRequest;
import br.com.insuranceacme.insuranceacme_api.domain.response.InsuranceQuoteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InsuranceQuoteService {

    Long create(final InsuranceQuoteRequest request);

    void update(final InsuranceQuoteEvent event);

    InsuranceQuoteResponse findById(final Long insuranceId);

    Page<InsuranceQuoteResponse> findAll(final Pageable pageable);
}
