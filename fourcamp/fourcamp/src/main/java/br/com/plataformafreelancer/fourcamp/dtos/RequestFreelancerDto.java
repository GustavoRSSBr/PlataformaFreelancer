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


    @Override
    public String toString() {
        return "RequestFreelancerDto{" +
                "email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", pais='" + pais + '\'' +
                ", cep='" + cep + '\'' +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", telefone='" + telefone + '\'' +
                ", descricao='" + descricao + '\'' +
                ", disponibilidade='" + disponibilidade + '\'' +
                ", habilidades=" + habilidades +
                '}';
    }
}
