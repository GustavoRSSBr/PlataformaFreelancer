package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.exceptions.SenhaInvalidaException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class SenhaService {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$"
    );

    public void validarSenha(String senha) {
        if (senha == null || !PASSWORD_PATTERN.matcher(senha).matches()) {
            throw new SenhaInvalidaException(ErrorCode.SENHA_INVALIDA.getCustomMessage());
        }
    }
}

