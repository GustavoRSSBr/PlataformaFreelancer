package br.com.plataformafreelancer.fourcamp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjetoCompatibilidadeDto {
    private int idProjeto;
    private String titulo;
    private String descricao;
    private String orcamento;
    private String prazo;
    private String status;
    private String habilidadesCompativeis;
    private String habilidadesNaoCompativeis;

    // Getters e Setters

    // Construtores (opcional)
}
