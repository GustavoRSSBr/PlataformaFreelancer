package br.com.plataformafreelancer.fourcamp.dtos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestAtualizarProjetoDto {
    private Integer idProjeto;
    private String titulo;
    private String descricao;
    private String orcamento;
    private String prazo;
    private List<String> habilidades;
}
