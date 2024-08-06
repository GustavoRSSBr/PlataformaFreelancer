package br.com.plataformafreelancer.fourcamp.dao.impl.mapper;

import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaCompletaDto;
import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmpresaCompletaDtoRowMapper implements RowMapper<ResponseEmpresaCompletaDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public ResponseEmpresaCompletaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<Avaliacao> avaliacoes = objectMapper.readValue(rs.getString("avaliacoes"), objectMapper.getTypeFactory().constructCollectionType(List.class, Avaliacao.class));
        List<Projeto> projetos = objectMapper.readValue(rs.getString("projetos"), objectMapper.getTypeFactory().constructCollectionType(List.class, Projeto.class));

        return ResponseEmpresaCompletaDto.builder()
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
                .notaMedia(rs.getDouble("nota_media"))
                .avaliacoes(avaliacoes)
                .projetos(projetos)
                .build();
    }
}
