package br.com.plataformafreelancer.fourcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TelefoneInvalidoException extends NegocioException {
    public TelefoneInvalidoException(String message) {
        super(message);
    }
}

