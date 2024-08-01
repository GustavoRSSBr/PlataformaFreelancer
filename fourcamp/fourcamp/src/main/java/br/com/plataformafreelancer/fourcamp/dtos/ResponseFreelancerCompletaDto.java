package br.com.plataformafreelancer.fourcamp.dtos;

import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFreelancerCompletaDto {
    private Integer idFreelancer;
    private String email;
    private String nome;
    private String dataNascimento;
    private String telefone;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String cep;
    private String estado;
    private String pais;
    private String descricao;
    private String disponibilidade;
    private String dataCriacao;
    private String statusFreelancer;
    private Double notaMedia;
    private List<String> habilidades;
    private List<Avaliacao> avaliacoes;
    private List<Projeto> projetos;
}
