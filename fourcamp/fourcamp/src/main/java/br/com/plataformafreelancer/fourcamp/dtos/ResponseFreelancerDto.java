package br.com.plataformafreelancer.fourcamp.dto;

import br.com.plataformafreelancer.fourcamp.enuns.StatusFreelancer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFreelancerDto {
    private Integer idFreelancer;
    private String email;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String telefone;
    private String cidade;
    private String estado;
    private String descricao;
    private String disponibilidade;
    private String dataCriacao;
    private StatusFreelancer statusFreelancer;
    private List<String> habilidades;
}

