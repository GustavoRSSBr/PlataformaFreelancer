package br.com.plataformafreelancer.fourcamp.dtos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestProjetoDto {
    private String titulo;
    private String descricao;
    private String orcamento;
    private String prazo;
    private Integer idEmpresa;
    private List<String> habilidades;
}
