package br.com.plataformafreelancer.fourcamp.dao.impl.mapper;

import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpresaDtoRowMapper implements RowMapper<ResponseEmpresaDto> {
    @Override
    public ResponseEmpresaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ResponseEmpresaDto.builder()
                .idEmpresa(rs.getInt("id_empresa"))
                .email(rs.getString("email"))
                .cnpj(rs.getString("cnpj"))
                .nome(rs.getString("nome"))
                .telefone(rs.getString("telefone"))
                .logradouro(rs.getString("logradouro"))
                .numero(rs.getString("numero"))
                .complemento(rs.getString("complemento"))
                .bairro(rs.getString("bairro"))
                .cidade(rs.getString("cidade"))
                .cep(rs.getString("cep"))
                .estado(rs.getString("estado"))
                .pais(rs.getString("pais"))
                .nomeEmpresa(rs.getString("nome_empresa"))
                .ramoAtuacao(rs.getString("ramo_atuacao"))
                .site(rs.getString("site"))
                .build();
    }
}
