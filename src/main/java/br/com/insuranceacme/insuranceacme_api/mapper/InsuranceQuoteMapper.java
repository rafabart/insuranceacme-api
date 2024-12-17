package br.com.insuranceacme.insuranceacme_api.mapper;


import br.com.insuranceacme.insuranceacme_api.domain.document.CustomerDocument;
import br.com.insuranceacme.insuranceacme_api.domain.document.InsuranceQuoteDocument;
import br.com.insuranceacme.insuranceacme_api.domain.event.CustomerEvent;
import br.com.insuranceacme.insuranceacme_api.domain.event.InsuranceQuoteEvent;
import br.com.insuranceacme.insuranceacme_api.domain.request.CustomerRequest;
import br.com.insuranceacme.insuranceacme_api.domain.request.InsuranceQuoteRequest;
import br.com.insuranceacme.insuranceacme_api.domain.response.CustomerResponse;
import br.com.insuranceacme.insuranceacme_api.domain.response.InsuranceQuoteResponse;
import br.com.insuranceacme.insuranceacme_api.tool.NumericIdGenerator;
import org.springframework.stereotype.Component;


@Component
public class InsuranceQuoteMapper {

    public InsuranceQuoteDocument to(final InsuranceQuoteRequest request) {
        return new InsuranceQuoteDocument(
                NumericIdGenerator.generateUniqueId(),
                null,
                request.getProductId(),
                request.getOfferId(),
                request.getCategory(),
                request.getTotalMonthlyPremiumAmount(),
                request.getTotalCoverageAmount(),
                request.getCoverages(),
                request.getAssistances(),
                to(request.getCustomer())
        );
    }

    public InsuranceQuoteEvent toEvent(final InsuranceQuoteDocument document) {
        return new InsuranceQuoteEvent(
                document.getId(),
                document.getInsurancePolicyId(),
                document.getProductId(),
                document.getOfferId(),
                document.getCategory(),
                document.getTotalMonthlyPremiumAmount(),
                document.getTotalCoverageAmount(),
                document.getCoverages(),
                document.getAssistances(),
                toEvent(document.getCustomer())
        );
    }

    public InsuranceQuoteDocument to(final InsuranceQuoteEvent event) {
        return new InsuranceQuoteDocument(
                event.getId(),
                event.getInsurancePolicyId(),
                event.getProductId(),
                event.getOfferId(),
                event.getCategory(),
                event.getTotalMonthlyPremiumAmount(),
                event.getTotalCoverageAmount(),
                event.getCoverages(),
                event.getAssistances(),
                toDocument(event.getCustomer())
        );
    }

    public InsuranceQuoteResponse to(final InsuranceQuoteDocument document) {
        return new InsuranceQuoteResponse(
                document.getId(),
                document.getInsurancePolicyId(),
                document.getProductId(),
                document.getOfferId(),
                document.getCategory(),
                document.getTotalMonthlyPremiumAmount(),
                document.getTotalCoverageAmount(),
                document.getCoverages(),
                document.getAssistances(),
                to(document.getCustomer())
        );
    }

    private CustomerDocument to(final CustomerRequest request) {
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

    private CustomerResponse to(final CustomerDocument document) {
        return new CustomerResponse(
                document.getDocumentNumber(),
                document.getName(),
                document.getType(),
                document.getGender(),
                document.getDateOfBirth(),
                document.getEmail(),
                document.getPhoneNumber()
        );
    }

    private CustomerEvent toEvent(final CustomerDocument document) {
        return new CustomerEvent(
                document.getDocumentNumber(),
                document.getName(),
                document.getType(),
                document.getGender(),
                document.getDateOfBirth(),
                document.getEmail(),
                document.getPhoneNumber()
        );
    }

    private CustomerDocument toDocument(final CustomerEvent event) {
        return new CustomerDocument(
                event.getDocumentNumber(),
                event.getName(),
                event.getType(),
                event.getGender(),
                event.getDateOfBirth(),
                event.getEmail(),
                event.getPhoneNumber()
        );
    }
}
