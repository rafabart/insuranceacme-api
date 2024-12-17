package br.com.insuranceacme.insuranceacme_api.service.impl;

import br.com.insuranceacme.insuranceacme_api.client.CatalogApiClient;
import br.com.insuranceacme.insuranceacme_api.domain.request.InsuranceQuoteRequest;
import br.com.insuranceacme.insuranceacme_api.domain.response.OfferResponse;
import br.com.insuranceacme.insuranceacme_api.domain.response.ProductResponse;
import br.com.insuranceacme.insuranceacme_api.exception.InsuranceQuoteValidationException;
import br.com.insuranceacme.insuranceacme_api.service.InsuranceValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class InsuranceValidationServiceImpl implements InsuranceValidationService {

    private final CatalogApiClient catalogApiClient;


    public void validate(final InsuranceQuoteRequest request) {
        log.info("InsuranceValidationServiceImpl.validate= {}", request);

        try {
            ProductResponse productResponse = catalogApiClient.getProduct(request.getProductId());
            OfferResponse offerResponse = catalogApiClient.getOffer(request.getOfferId());

            hasOffer(offerResponse);
            hasProduct(productResponse);
            hasCoverages(request, offerResponse);
            hasMaxAmountCoveragesAllowed(request, offerResponse);
            hasAssistances(request, offerResponse);
            hasTotalMonthlyPremiumAmountAllowed(request, offerResponse);
            hasTotalCoverageAmountMatchWithOffer(request);

        } catch (RuntimeException e) {
            throw e;
        }

    }

    private void hasOffer(final OfferResponse offerResponse) {
        if (!offerResponse.getActive()) throw new InsuranceQuoteValidationException("Oferta não ativa!");
    }

    private void hasProduct(final ProductResponse productResponse) {
        if (!productResponse.getActive()) throw new InsuranceQuoteValidationException("Produto não ativo!");
    }

    private void hasCoverages(final InsuranceQuoteRequest request, final OfferResponse offerResponse) {
        if (!offerResponse.getCoverages().keySet().containsAll(request.getCoverages().keySet()))
            throw new InsuranceQuoteValidationException("As coberturas informadas estão fora da lista de coberturas da oferta");
    }

    private void hasMaxAmountCoveragesAllowed(final InsuranceQuoteRequest request,
                                              final OfferResponse offerResponse) {
        final Set<String> keys = request.getCoverages().keySet();
        if (keys.stream().anyMatch(it -> request.getCoverages().get(it) > offerResponse.getCoverages().get(it)))
            throw new InsuranceQuoteValidationException("As coberturas informadas estão abaixo dos valores máximos permitidos.");
    }

    private void hasAssistances(final InsuranceQuoteRequest request, final OfferResponse offerResponse) {
        if (!offerResponse.getAssistances().containsAll(request.getAssistances()))
            throw new InsuranceQuoteValidationException("As assistências informadas estão fora da lista de assistências da oferta");
    }

    private void hasTotalMonthlyPremiumAmountAllowed(final InsuranceQuoteRequest request,
                                                     final OfferResponse offerResponse) {
        if (!(request.getTotalMonthlyPremiumAmount() >= offerResponse.getMonthlyPremiumAmount().getMinAmount() &&
                request.getTotalMonthlyPremiumAmount() <= offerResponse.getMonthlyPremiumAmount().getMaxAmount()))
            throw new InsuranceQuoteValidationException("O valor total do prêmio mensal não está entre o máximo e mínimo definido\n" +
                    "para a oferta");
    }

    private void hasTotalCoverageAmountMatchWithOffer(final InsuranceQuoteRequest request) {
        if (!(request.getTotalCoverageAmount() == request.getCoverages().values().stream().mapToDouble(Double::doubleValue).sum()))
            throw new InsuranceQuoteValidationException("O valor total das coberturas corresponde a somatória das coberturas\n" +
                    "informadas não corresponde ao valor total informado das coberturas no\n" +
                    "corpo da requisição");
    }
}
