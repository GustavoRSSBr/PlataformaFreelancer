package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.IFreelancerJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.*;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseProjetoCompatibilidadeDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAtualizarFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaDto;
import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Freelancer;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.Proposta;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreelancerJdbcTemplateDaoImpl implements IFreelancerJdbcTemplateDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreelancerJdbcTemplateDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void salvarDadosCadastrais(Freelancer freelancer) {
        LoggerUtils.logRequestStart(LOGGER, "salvarDadosCadastrais", freelancer);
        String sql = "CALL cadastrar_freelancer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                freelancer.getUsuario().getEmail(),
                freelancer.getUsuario().getSenha(),
                freelancer.getUsuario().getTipoUsuario().toString(),
                freelancer.getEndereco().getLogradouro(),
                freelancer.getEndereco().getNumero(),
                freelancer.getEndereco().getComplemento(),
                freelancer.getEndereco().getBairro(),
                freelancer.getEndereco().getCidade(),
                freelancer.getEndereco().getCep(),
                freelancer.getEndereco().getEstado(),
                freelancer.getEndereco().getPais(),
                freelancer.getNome(),
                freelancer.getCpf(),
                freelancer.getDataNascimento().toString(),
                freelancer.getTelefone(),
                freelancer.getDescricao(),
                freelancer.getDisponibilidade(),
                freelancer.getDataCriacao(),
                freelancer.getStatusFreelancer().toString(),
                freelancer.getHabilidades().toArray(new String[0])
        );
        LoggerUtils.logElapsedTime(LOGGER, "salvarDadosCadastrais", System.currentTimeMillis());
    }

    @Override
    public void salvarProposta(Proposta proposta) {
        LoggerUtils.logRequestStart(LOGGER, "salvarProposta", proposta);
        String sql = "CALL criar_proposta(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                proposta.getFreelancerId(),
                proposta.getProjetoId(),
                proposta.getValor(),
                proposta.getDataCriacao(),
                proposta.getStatusProposta().toString(),
                proposta.getObservacao()
        );
        LoggerUtils.logElapsedTime(LOGGER, "salvarProposta", System.currentTimeMillis());
    }

    @Override
    public void avaliarEmpresa(Avaliacao avaliacao) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarEmpresa", avaliacao);
        String sql = "CALL enviar_avaliacao(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                avaliacao.getEmpresaId(),
                avaliacao.getFreelancerId(),
                avaliacao.getProjetoId(),
                avaliacao.getAvaliado().toString(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getDataAvaliacao()
        );
        LoggerUtils.logElapsedTime(LOGGER, "avaliarEmpresa", System.currentTimeMillis());
    }

    public List<ResponseEmpresaDto> listarEmpresas() {
        String sql = "SELECT * FROM listar_empresas()";
        return jdbcTemplate.query(sql, new EmpresaDtoRowMapper());
    }

    @Override
    public List<Projeto> listarTodosProjetos() {
        String sql = "SELECT * FROM listar_todos_projetos()";
        return jdbcTemplate.query(sql, new ProjetoDtoRowMapper());
    }

    @Override
    public ResponseEmpresaCompletaDto obterDetalhesEmpresa(Integer empresaId) {
        String sql = "SELECT * FROM obter_detalhes_empresa(?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{empresaId}, new EmpresaCompletaDtoRowMapper());
    }

    @Override
    public List<ResponseProjetoCompatibilidadeDto> buscarProjetosCompativeis(int idFreelancer) {
        LoggerUtils.logRequestStart(LOGGER, "buscarProjetosCompativeis", idFreelancer);
        String sql = "SELECT * FROM buscar_projetos_compatíveis(?)";
        return jdbcTemplate.query(sql, new Object[]{idFreelancer}, new ProjetoCompatibilidadeDtoRowMapper());
    }

    public void atualizarDadosFreelancer(RequestAtualizarFreelancerDto request) {
        LoggerUtils.logRequestStart(LOGGER, "atualizarDadosFreelancer", request);
        String sql = "CALL atualizar_freelancer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                request.getIdFreelancer(),
                request.getTelefone(),
                request.getLogradouro(),
                request.getNumero(),
                request.getComplemento(),
                request.getBairro(),
                request.getCidade(),
                request.getCep(),
                request.getEstado(),
                request.getPais(),
                request.getDescricao(),
                request.getDisponibilidade(),
                request.getStatus(),
                request.getHabilidades().toArray(new String[0])
        );
        LoggerUtils.logElapsedTime(LOGGER, "atualizarDadosFreelancer", System.currentTimeMillis());
    }
}
