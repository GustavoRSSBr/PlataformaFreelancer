package br.com.plataformafreelancer.fourcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException(String message) {
        super(message);
    }
}
