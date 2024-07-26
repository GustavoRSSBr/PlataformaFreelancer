package br.com.plataformafreelancer.fourcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class DataInvalidaException extends NegocioException {
    public DataInvalidaException(String message) {
        super(message);
    }
}

