package br.com.plataformafreelancer.fourcamp.model;

import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Proposta {
    private Integer propostaId;
    private Integer freelancerId;
    private Integer projetoId;
    private String valor;
    private String dataCriacao;
    private StatusProposta statusProposta;
    private String observacao;
}

