package br.com.plataformafreelancer.fourcamp.dao.impl.mapper;

import br.com.plataformafreelancer.fourcamp.dtos.ResponsePropostaDto;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropostaRowMapper implements RowMapper<ResponsePropostaDto> {
    @Override
    public ResponsePropostaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ResponsePropostaDto.builder()
                .propostaId(rs.getInt("propostaId"))
                .freelancerNome(rs.getString("freelancerNome"))
                .freelancerTelefone(rs.getString("freelancerTelefone"))
                .freelancerEmail(rs.getString("freelancerEmail"))
                .projetoId(rs.getInt("projetoId"))
                .valor(rs.getString("valor"))
                .dataCriacao(rs.getString("dataCriacao"))
                .statusProposta(StatusProposta.valueOf(rs.getString("status")))
                .observacao(rs.getString("observacao"))
                .build();
    }
}

