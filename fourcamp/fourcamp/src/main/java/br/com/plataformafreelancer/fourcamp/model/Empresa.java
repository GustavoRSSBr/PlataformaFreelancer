package br.com.plataformafreelancer.fourcamp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {
    private Integer idEmpresa;
    private Usuario usuario;
    private String cnpj;
    private String nome;
    private String telefone;
    private Endereco endereco;
    private String nomeEmpresa;
    private String ramoAtuacao;
    private String site;
    private Double nota;
    private List<Avaliacao> avaliacoes;
    private List<Projeto> projetos;
}

