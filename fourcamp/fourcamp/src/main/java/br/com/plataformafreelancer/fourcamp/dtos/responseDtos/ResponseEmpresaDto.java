package br.com.plataformafreelancer.fourcamp.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEmpresaDto {
    private Integer idEmpresa;
    private String email;
    private String cnpj;
    private String nome;
    private String telefone;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String cep;
    private String estado;
    private String pais;
    private String nomeEmpresa;
    private String ramoAtuacao;
    private String site;
}
