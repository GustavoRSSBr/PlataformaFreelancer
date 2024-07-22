package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.exceptions.TelefoneInvalidoException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class TelefoneService {

    // Regex para validar números de telefone com caracteres especiais permitidos
    private static final String PHONE_NUMBER_PATTERN = "^[0-9()\\-\\s]+$";
    private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

    public void validarNumeroTelefone(String numeroTelefone) {
        if (numeroTelefone == null || numeroTelefone.trim().isEmpty() || !pattern.matcher(numeroTelefone).matches()) {
            throw new TelefoneInvalidoException("Número de telefone inválido: " + numeroTelefone);
        }
    }
}


