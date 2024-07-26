package br.com.plataformafreelancer.fourcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SenhaInvalidaException extends NegocioException {
    public SenhaInvalidaException(String message) {
        super(message);
    }
}
