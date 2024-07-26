package br.com.plataformafreelancer.fourcamp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestFreelancerDto {
    private String email;
    private String senha;
    private String numero;
    private String complemento;
    private String pais;
    private String cep;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String telefone;
    private String descricao;
    private String disponibilidade;
    private List<String> habilidades;
}
