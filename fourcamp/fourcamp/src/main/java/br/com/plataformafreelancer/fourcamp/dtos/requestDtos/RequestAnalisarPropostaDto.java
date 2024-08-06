package br.com.plataformafreelancer.fourcamp.dtos.requestDtos;

import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestAnalisarPropostaDto {
    private Integer idProposta;
    private StatusProposta statusProposta;
}
