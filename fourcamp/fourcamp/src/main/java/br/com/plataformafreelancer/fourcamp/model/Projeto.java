package br.com.plataformafreelancer.fourcamp.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {
    private int idProjeto;
    private String titulo;
    private String descricao;
    private String orcamento;
    private String prazo;
    private String status;
    private String dataCriacao;
    private Integer empresaId;
    private Integer freelancerId;

    
}



