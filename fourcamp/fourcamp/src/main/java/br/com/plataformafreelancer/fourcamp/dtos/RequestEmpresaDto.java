package br.com.plataformafreelancer.fourcamp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestEmpresaDto {
    private String email;
    private String senha;
    private String numero;
    private String complemento;
    private String pais;
    private String cep;
    private String nome;
    private String cnpj;
    private String telefone;
    private String nomeEmpresa;
    private String ramoAtuacao;
    private String site;

    @Override
    public String toString() {
        return "RequestEmpresaDto{" +
                "email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", pais='" + pais + '\'' +
                ", cep='" + cep + '\'' +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", telefone='" + telefone + '\'' +
                ", nomeEmpresa='" + nomeEmpresa + '\'' +
                ", ramoAtuacao='" + ramoAtuacao + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}

