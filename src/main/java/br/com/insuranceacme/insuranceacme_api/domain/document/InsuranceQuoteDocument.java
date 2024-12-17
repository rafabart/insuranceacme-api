package br.com.insuranceacme.insuranceacme_api.domain.document;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Set;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "Insurance_quote")
public class InsuranceQuoteDocument {

    @Id
    private Long id;

    private Long insurancePolicyId;

    @NotNull
    private String productId;

    @NotNull
    private String offerId;

    @NotNull
    private String category;

    @NotNull
    private Double totalMonthlyPremiumAmount;

    @NotNull
    private Double totalCoverageAmount;

    @NotNull
    private HashMap<String, Double> coverages;

    @NotNull
    private Set<String> assistances;

    @NotNull
    private CustomerDocument customer;
}
