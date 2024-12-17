package br.com.insuranceacme.insuranceacme_api.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(final String message) {
        super(message + " não encontrado(a)!");
    }

    public ObjectNotFoundException(final String message, final Throwable cause) {

        super(message + " não encontrado(a)!", cause);
    }
}
