package br.com.insuranceacme.insuranceacme_api.exception;

import br.com.insuranceacme.insuranceacme_api.domain.exception.StandardError;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerExceptionResource {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(final ObjectNotFoundException e, final HttpServletRequest request) {

        final StandardError standardError = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
                "Objeto não encontrado", e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }


    @ExceptionHandler(InsuranceQuoteValidationException.class)
    public ResponseEntity<StandardError> validation(final InsuranceQuoteValidationException e, final HttpServletRequest request) {

        final StandardError standardError = new StandardError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de Validação", e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standardError);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<StandardError> validation(final FeignException e, final HttpServletRequest request) {

        final StandardError standardError = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro na integração com o Catalogo Serviços", e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardError);
    }
}