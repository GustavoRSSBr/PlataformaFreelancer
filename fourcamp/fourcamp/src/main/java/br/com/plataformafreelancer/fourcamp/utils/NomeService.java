package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.exceptions.NomeInvalidoException;
import org.springframework.stereotype.Service;

@Service
public class NomeService {

    public void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty() || !nome.matches("^[A-Za-zÀ-ÿ ]+$")) {
            throw new NomeInvalidoException(ErrorCode.NOME_INVALIDO.getCustomMessage() + nome);
        }
    }
}

