package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.exceptions.CnpjInvalidoException;
import org.springframework.stereotype.Service;

@Service
public class CnpjService {

    public void validarCnpj(String cnpj) {
        if (cnpj == null || !isValidCNPJ(cnpj)) {
            throw new CnpjInvalidoException("CNPJ inválido: " + cnpj);
        }
    }

    private boolean isValidCNPJ(String cnpj) {
        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("\\D", "");

        // Verifica se o CNPJ tem 14 dígitos
        if (cnpj.length() != 14) {
            return false;
        }

        // Verificar se todos os dígitos são iguais (ex. 11.111.111/1111-11)
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        char dig13, dig14;
        int sm, i, r, num, peso;

        // Cálculo do primeiro dígito verificador
        sm = 0;
        peso = 2;
        for (i = 11; i >= 0; i--) {
            num = (int) (cnpj.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10) {
                peso = 2;
            }
        }

        r = sm % 11;
        if ((r == 0) || (r == 1)) {
            dig13 = '0';
        } else {
            dig13 = (char) ((11 - r) + 48);
        }

        // Cálculo do segundo dígito verificador
        sm = 0;
        peso = 2;
        for (i = 12; i >= 0; i--) {
            num = (int) (cnpj.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10) {
                peso = 2;
            }
        }

        r = sm % 11;
        if ((r == 0) || (r == 1)) {
            dig14 = '0';
        } else {
            dig14 = (char) ((11 - r) + 48);
        }

        // Verifica se os dígitos calculados conferem com os dígitos informados
        return (dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13));
    }
}
