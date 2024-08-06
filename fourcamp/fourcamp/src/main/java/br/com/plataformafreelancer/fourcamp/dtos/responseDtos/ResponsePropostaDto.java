package br.com.plataformafreelancer.fourcamp.dtos.responseDtos;

import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePropostaDto {
    private Integer propostaId;
    private String freelancerNome;
    private String freelancerTelefone;
    private String freelancerEmail;
    private Integer projetoId;
    private String valor;
    private String dataCriacao;
    private StatusProposta statusProposta;
    private String observacao;
}
