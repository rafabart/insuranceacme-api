package br.com.insuranceacme.insuranceacme_api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyPremiumAmountResponse {

    @JsonProperty("max_amount")
    private Double maxAmount;

    @JsonProperty("min_amount")
    private Double minAmount;

    @JsonProperty("suggested_amount")
    private Double suggestedAmount;
}