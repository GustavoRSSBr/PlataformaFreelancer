package br.com.plataformafreelancer.fourcamp.dao.impl.mapper;

import br.com.plataformafreelancer.fourcamp.enuns.StatusFreelancer;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseFreelancerDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class FreelancerDtoRowMapper implements RowMapper<ResponseFreelancerDto> {
    @Override
    public ResponseFreelancerDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ResponseFreelancerDto.builder()
                .idFreelancer(rs.getInt("idFreelancer"))
                .email(rs.getString("email"))
                .nome(rs.getString("nome"))
                .dataNascimento(rs.getString("dataNascimento"))
                .telefone(rs.getString("telefone"))
                .cidade(rs.getString("cidade"))
                .estado(rs.getString("estado"))
                .descricao(rs.getString("descricao"))
                .disponibilidade(rs.getString("disponibilidade"))
                .dataCriacao(rs.getString("dataCriacao"))
                .statusFreelancer(StatusFreelancer.valueOf(rs.getString("status").toUpperCase()))
                .habilidades(Arrays.asList(rs.getString("habilidades").split(",")))
                .build();
    }
}
