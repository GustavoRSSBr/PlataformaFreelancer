package br.com.plataformafreelancer.fourcamp.model;

import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Integer idUsuario;
    private String email;
    private String senha;
    private TipoUsuario tipoUsuario;
}
