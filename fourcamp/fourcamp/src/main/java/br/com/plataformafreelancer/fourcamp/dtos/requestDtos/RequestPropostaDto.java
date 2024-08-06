package br.com.plataformafreelancer.fourcamp.dtos.requestDtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestPropostaDto {
    private Integer freelancerId;
    private Integer projetoId;
    private String valor;
    private String observacao;
}

