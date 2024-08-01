package br.com.plataformafreelancer.fourcamp.dao.impl.mapper;

import br.com.plataformafreelancer.fourcamp.dtos.ProjetoCompatibilidadeDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjetoCompatibilidadeDtoRowMapper implements RowMapper<ProjetoCompatibilidadeDto> {

    @Override
    public ProjetoCompatibilidadeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProjetoCompatibilidadeDto projeto = new ProjetoCompatibilidadeDto();
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
