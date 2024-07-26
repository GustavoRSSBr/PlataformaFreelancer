package br.com.plataformafreelancer.fourcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NomeInvalidoException extends NegocioException{
    public NomeInvalidoException(String message) {
        super(message);
    }
}

