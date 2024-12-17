package br.com.insuranceacme.insuranceacme_api.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardError {

    private Long timeStamp;

    private Integer status;

    private String error;

    private String message;

    private String path;
}