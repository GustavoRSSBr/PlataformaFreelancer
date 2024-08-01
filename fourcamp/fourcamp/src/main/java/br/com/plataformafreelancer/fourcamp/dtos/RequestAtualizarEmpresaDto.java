package br.com.plataformafreelancer.fourcamp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestAtualizarEmpresaDto {
    private int idEmpresa;
    private String nome;
    private String telefone;
    private String ramoAtuacao;
    private String site;
}
