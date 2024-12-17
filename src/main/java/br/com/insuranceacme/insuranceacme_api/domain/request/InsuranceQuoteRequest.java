package br.com.insuranceacme.insuranceacme_api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceQuoteRequest {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("offer_id")
    private String offerId;

    private String category;

    @JsonProperty("total_monthly_premium_amount")
    private Double totalMonthlyPremiumAmount;

    @JsonProperty("total_coverage_amount")
    private Double totalCoverageAmount;


    private HashMap<String, Double> coverages;


    private Set<String> assistances;


    private CustomerRequest customer;
}

