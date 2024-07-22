package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.exceptions.CpfInvalidoException;
import org.springframework.stereotype.Service;

@Service
public class CpfService {

    public void validarCpf(String cpf) {
        if (cpf == null || !isValidCPF(cpf)) {
            throw new CpfInvalidoException("CPF inválido: " + cpf);
        }
    }

    private boolean isValidCPF(String cpf) {
        // Remover caracteres não numéricos
        cpf = cpf.replaceAll("[^\\d]", "");

        // Verificar se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verificar se todos os dígitos são iguais (ex. 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcular os dígitos verificadores
        int[] digits = new int[11];
        for (int i = 0; i < 11; i++) {
            digits[i] = Character.getNumericValue(cpf.charAt(i));
        }

        // Calcular primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }
        int firstVerifier = 11 - (sum % 11);
        if (firstVerifier >= 10) {
            firstVerifier = 0;
        }

        // Calcular segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += digits[i] * (11 - i);
        }
        int secondVerifier = 11 - (sum % 11);
        if (secondVerifier >= 10) {
            secondVerifier = 0;
        }

        // Verificar se os dígitos verificadores estão corretos
        return digits[9] == firstVerifier && digits[10] == secondVerifier;
    }
}

