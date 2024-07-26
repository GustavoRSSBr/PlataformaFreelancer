package br.com.plataformafreelancer.fourcamp.utils;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.util.Arrays;

public class JsonErrorUtils {

    public static String getErrorMessage(Exception e) {
        if (e.getCause() instanceof InvalidFormatException invalidFormatException) {
            String targetType = invalidFormatException.getTargetType().getSimpleName();
            String value = invalidFormatException.getValue().toString();
            return "Não é possível desserializar o valor \"" + value + "\" para o tipo " + targetType + ". Valores aceitos: " +
                    Arrays.toString(invalidFormatException.getTargetType().getEnumConstants());
        }
        return "Erro ao desserializar o JSON: " + e.getMessage();
    }
}
