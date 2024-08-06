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
public class RequestAtualizarFreelancerDto {
    private int idFreelancer;
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
    private String status;
    private List<String> habilidades;

    // Getters e Setters

    // Construtores (opcional)
}
