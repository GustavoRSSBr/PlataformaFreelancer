package br.com.plataformafreelancer.fourcamp.dao.impl.mapper;

import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProjeto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ProjetoDtoRowMapper implements RowMapper<Projeto> {
    @Override
    public Projeto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Projeto.builder()
                .idProjeto(rs.getInt("id_projeto"))
                .titulo(rs.getString("titulo"))
                .descricao(rs.getString("descricao"))
                .orcamento(rs.getString("orcamento"))
                .prazo(rs.getString("prazo"))
                .statusProjeto(StatusProjeto.valueOf(rs.getString("status")))
                .dataCriacao(rs.getString("data_criacao"))
                .empresaId(rs.getInt("empresa_id"))
                .freelancerId(rs.getInt("freelancer_id"))
                .habilidades(Arrays.asList(rs.getString("habilidades").split(",")))
                .build();
    }
}
