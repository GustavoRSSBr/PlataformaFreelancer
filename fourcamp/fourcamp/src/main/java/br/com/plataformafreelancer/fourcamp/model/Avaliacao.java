package br.com.plataformafreelancer.fourcamp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Avaliacao {
    private Integer idAvaliacao;
    private Integer empresaId;
    private Integer freelancerId;
    private String avaliado;
    private Integer nota;
    private String comentario;
    private String dataAvaliacao;

    @Override
    public String toString() {
        return "Avaliacao{" +
                "idAvaliacao=" + idAvaliacao +
                ", empresaId=" + empresaId +
                ", freelancerId=" + freelancerId +
                ", avaliado='" + avaliado + '\'' +
                ", nota=" + nota +
                ", comentario='" + comentario + '\'' +
                ", dataAvaliacao='" + dataAvaliacao + '\'' +
                '}';
    }
}
