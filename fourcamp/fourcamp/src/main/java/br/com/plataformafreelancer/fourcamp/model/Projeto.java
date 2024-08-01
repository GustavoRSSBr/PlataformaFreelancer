package br.com.plataformafreelancer.fourcamp.model;

import br.com.plataformafreelancer.fourcamp.enuns.StatusProjeto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

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
    private StatusProjeto statusProjeto;
    private String dataCriacao;
    private Integer empresaId;
    private Integer freelancerId;
    private List<String> habilidades;
}



