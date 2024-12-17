package br.com.insuranceacme.insuranceacme_api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private String id;

    private String name;

    @JsonProperty("created_at")
    private String createdAt;

    private Boolean active;

    private Set<String> offers;
}
