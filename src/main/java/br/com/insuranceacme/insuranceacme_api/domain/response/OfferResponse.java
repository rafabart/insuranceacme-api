package br.com.insuranceacme.insuranceacme_api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferResponse {

    private String id;

    @JsonProperty("product_id")
    private String productId;

    private String name;

    @JsonProperty("created_at")
    private String createdAt;

    private Boolean active;

    private HashMap<String, Double> coverages;

    private Set<String> assistances;

    @JsonProperty("monthly_premium_amount")
    private MonthlyPremiumAmountResponse monthlyPremiumAmount;
}
