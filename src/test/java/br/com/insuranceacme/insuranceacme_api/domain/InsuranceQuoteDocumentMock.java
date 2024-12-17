package br.com.insuranceacme.insuranceacme_api.domain;

import br.com.insuranceacme.insuranceacme_api.domain.document.CustomerDocument;
import br.com.insuranceacme.insuranceacme_api.domain.document.InsuranceQuoteDocument;
import br.com.insuranceacme.insuranceacme_api.domain.request.CustomerRequest;
import br.com.insuranceacme.insuranceacme_api.domain.request.InsuranceQuoteRequest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InsuranceQuoteDocumentMock {

    public static InsuranceQuoteDocument build(final InsuranceQuoteRequest request) {
        return new InsuranceQuoteDocument(
                123456L,
                null,
                request.getProductId(),
                request.getOfferId(),
                request.getCategory(),
                request.getTotalMonthlyPremiumAmount(),
                request.getTotalCoverageAmount(),
                request.getCoverages(),
                request.getAssistances(),
                getCustomer(request.getCustomer())
        );
    }

    public static InsuranceQuoteDocument build() {
        return new InsuranceQuoteDocument(
                123456L,
                null,
                "1b2da7cc-b367-4196-8a78-9cfeec21f587",
                "adc56d77-348c-4bf0-908f-22d402ee715c",
                "HOME",
                75.25,
                825000.00,
                getCoverages(),
                getAssistances(),
                getCustomer()
        );
    }

    private static HashMap<String, Double> getCoverages() {
        HashMap<String, Double> coverages = new HashMap<>();
        coverages.put("Incêndio", 250000.00);
        coverages.put("Desastres naturais", 500000.00);
        coverages.put("Responsabiliadade civil", 75000.00);
        return coverages;
    }


    private static Set<String> getAssistances() {
        Set<String> assistances = new HashSet<>();
        assistances.add("Encanador");
        assistances.add("Eletricista");
        assistances.add("Chaveiro 24h");
        return assistances;
    }

    private static CustomerDocument getCustomer() {
        return new CustomerDocument(
                "36205578900",
                "John Wick",
                "NATURAL",
                "MALE",
                "1973-05-02",
                "johnwick@gmail.com",
                11950503030L
        );
    }

    private static CustomerDocument getCustomer(final CustomerRequest request) {
        return new CustomerDocument(
                request.getDocumentNumber(),
                request.getName(),
                request.getType(),
                request.getGender(),
                request.getDateOfBirth(),
                request.getEmail(),
                request.getPhoneNumber()
        );
    }
}

