package br.com.plataformafreelancer.fourcamp.handler;

import br.com.plataformafreelancer.fourcamp.dtos.ErrorResponseDto;
import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.exceptions.NegocioException;
import br.com.plataformafreelancer.fourcamp.utils.JsonErrorUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleNegocioException(NegocioException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String errorMessage = JsonErrorUtils.getErrorMessage(e);
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleDataAccessException(DataAccessException e) {
        String errorMessage = e.getMostSpecificCause().getMessage();
        ErrorCode errorCode = ErrorCode.fromMessage(errorMessage);

        if (errorCode == ErrorCode.OUTRO_ERRO) {
            ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Outro erro: " + errorMessage);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), errorCode.getCustomMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
