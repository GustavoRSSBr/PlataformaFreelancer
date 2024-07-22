package br.com.plataformafreelancer.fourcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class CepNaoEncontradoException extends RuntimeException {
    public CepNaoEncontradoException(String message) {
        super(message);
    }
}

