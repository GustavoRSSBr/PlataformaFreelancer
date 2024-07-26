package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.exceptions.DataInvalidaException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class DataService {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public void validarDataNascimento(String dataNascimento) {
        if (dataNascimento == null || !isValidDate(dataNascimento)) {
            throw new DataInvalidaException("Data de nascimento inválida: " + dataNascimento);
        }
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);
            return parsedDate.isBefore(LocalDate.now()); // A data deve ser anterior à data atual
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public LocalDate converterParaLocalDate(String data) {
        return LocalDate.parse(data, DATE_FORMATTER);
    }

    public String coletarDataHoraAtual() {
        LocalDateTime agora = LocalDateTime.now();
        return agora.format(DATE_TIME_FORMATTER);
    }


}

