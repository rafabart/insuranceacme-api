package br.com.insuranceacme.insuranceacme_api.service;

import br.com.insuranceacme.insuranceacme_api.domain.request.InsuranceQuoteRequest;

public interface InsuranceValidationService {

    void validate(final InsuranceQuoteRequest request);

}
