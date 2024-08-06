package br.com.plataformafreelancer.fourcamp.dtos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestAvaliacaoDto {
    private Integer empresaId;
    private Integer freelancerId;
    private Integer projetoId;
    private Integer nota;
    private String comentario;
}
