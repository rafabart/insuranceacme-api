package br.com.insuranceacme.insuranceacme_api.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    @JsonProperty("document_number")
    private String documentNumber;

    private String name;

    private String type;

    private String gender;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    private String email;

    @JsonProperty("phone_number")
    private Long phoneNumber;
}
