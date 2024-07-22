package br.com.plataformafreelancer.fourcamp.handler;
import br.com.plataformafreelancer.fourcamp.dtos.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.Method;
import org.springframework.http.ResponseEntity;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception e) {
        HttpStatus status = getStatus(e);
        ErrorResponseDto errorResponse = new ErrorResponseDto(status.value(), e.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    private HttpStatus getStatus(Exception e) {
        // Verifica se a exceção tem a anotação @ResponseStatus
        ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);
        if (responseStatus != null) {
            return responseStatus.value();
        }

        // Verifica se algum método da exceção tem a anotação @ResponseStatus
        for (Method method : e.getClass().getMethods()) {
            responseStatus = method.getAnnotation(ResponseStatus.class);
            if (responseStatus != null) {
                return responseStatus.value();
            }
        }

        // Retorna o status interno do servidor por padrão
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }








    /*@ExceptionHandler(PSQLException.class)
    @ResponseBody
    public ErrorResponseDto handlePSQLException(PSQLException e) {
        // Exemplo de verificação de códigos SQL específicos
        if (e.getSQLState().equals("23505")) {
            // Violação de restrição única
            return new ErrorResponseDto(HttpStatus.CONFLICT.value(), "Entrada duplicada encontrada: " + e.getMessage());
        } else if (e.getSQLState().equals("23503")) {
            // Violação de restrição de chave estrangeira
            return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "Violação de restrição de chave estrangeira: " + e.getMessage());
        } else if (e.getSQLState().equals("22001")) {
            // Violação de comprimento do atributo
            return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "Comprimento do atributo inválido: " + e.getMessage());
        } else if (e.getSQLState().equals("23502")) {
            // Violação de atributo não nulo
            return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "Violação de atributo não nulo: " + e.getMessage());
        }
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro de banco de dados: " + e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ErrorResponseDto handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "Violação de integridade dos dados: " + e.getMessage());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseBody
    public ErrorResponseDto handleEmptyResultDataAccess(EmptyResultDataAccessException e) {
        return new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), "Recurso não encontrado: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponseDto handleGeneralException(Exception e) {
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro inesperado: " + e.getMessage());
    }*/






}


