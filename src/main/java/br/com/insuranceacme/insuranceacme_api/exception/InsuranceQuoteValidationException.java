package br.com.insuranceacme.insuranceacme_api.exception;

public class InsuranceQuoteValidationException extends RuntimeException {

    public InsuranceQuoteValidationException(final String message) {
        super(message);
    }

    public InsuranceQuoteValidationException(final String message, final Throwable cause) {

        super(message, cause);
    }
}
