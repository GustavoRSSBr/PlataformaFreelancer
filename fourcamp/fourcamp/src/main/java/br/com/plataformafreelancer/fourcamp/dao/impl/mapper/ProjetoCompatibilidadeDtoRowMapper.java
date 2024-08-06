package br.com.plataformafreelancer.fourcamp.dao.impl.mapper;

import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseProjetoCompatibilidadeDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjetoCompatibilidadeDtoRowMapper implements RowMapper<ResponseProjetoCompatibilidadeDto> {

    @Override
    public ResponseProjetoCompatibilidadeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResponseProjetoCompatibilidadeDto projeto = new ResponseProjetoCompatibilidadeDto();
        projeto.setIdProjeto(rs.getInt("idProjeto"));
        projeto.setTitulo(rs.getString("titulo"));
        projeto.setDescricao(rs.getString("descricao"));
        projeto.setOrcamento(rs.getString("orcamento"));
        projeto.setPrazo(rs.getString("prazo"));
        projeto.setStatus(rs.getString("status"));
        projeto.setHabilidadesCompativeis(rs.getString("habilidades_compativeis"));
        projeto.setHabilidadesNaoCompativeis(rs.getString("habilidades_nao_compativeis"));
        return projeto;
    }
}
