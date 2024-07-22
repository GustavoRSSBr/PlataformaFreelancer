package br.com.plataformafreelancer.fourcamp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponseDto {
    private int status;
    private String message;
}

