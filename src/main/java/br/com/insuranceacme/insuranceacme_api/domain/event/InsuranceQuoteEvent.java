package br.com.insuranceacme.insuranceacme_api.domain.event;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.Set;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InsuranceQuoteEvent {

    @Id
    private Long id;

    @JsonProperty("insurance_policy_id")
    private Long insurancePolicyId;

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


    private CustomerEvent customer;
}
