package br.com.plataformafreelancer.fourcamp.model;

import br.com.plataformafreelancer.fourcamp.enuns.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Freelancer {
    private Integer idFreelancer;
    private Usuario usuario;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private Endereco endereco;
    private String descricao;
    private String disponibilidade;
    private String dataCriacao;
    private Status status;
    private List<String> habilidades;
    private List<Avaliacao> avaliacoes;

    @Override
    public String toString() {
        return "Freelancer{" +
                "idFreelancer=" + idFreelancer +
                ", usuario=" + usuario +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", telefone='" + telefone + '\'' +
                ", endereco=" + endereco +
                ", descricao='" + descricao + '\'' +
                ", disponibilidade='" + disponibilidade + '\'' +
                ", dataCriacao='" + dataCriacao + '\'' +
                ", status=" + status +
                ", habilidades=" + habilidades +
                ", avaliacoes=" + avaliacoes +
                '}';
    }
}
