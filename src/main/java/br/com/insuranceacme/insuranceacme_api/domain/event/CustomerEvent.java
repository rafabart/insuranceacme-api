package br.com.insuranceacme.insuranceacme_api.domain.event;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerEvent {

    @NotNull
    private String documentNumber;

    @NotNull
    private String name;

    @NotNull
    private String type;

    @NotNull
    private String gender;

    @NotNull
    private String dateOfBirth;

    @NotNull
    private String email;

    @NotNull
    private Long phoneNumber;
}
