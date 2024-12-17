package br.com.insuranceacme.insuranceacme_api.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldMessageError {

    private String fieldName;

    private String message;
}
