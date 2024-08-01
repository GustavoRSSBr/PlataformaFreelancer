package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.IFreelancerJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.*;
import br.com.plataformafreelancer.fourcamp.dtos.ProjetoCompatibilidadeDto;
import br.com.plataformafreelancer.fourcamp.dtos.RequestAtualizarFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.ResponseEmpresaCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.ResponseEmpresaDto;
import br.com.plataformafreelancer.fourcamp.handler.GlobalExceptionHandler;
import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Freelancer;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.Proposta;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

        try {
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
        } catch (DataAccessException e) {
            LoggerUtils.logError(LOGGER, "salvarDadosCadastrais", freelancer, e);
            GlobalExceptionHandler.handleException(e);
        }
    }

    @Override
    public void salvarProposta(Proposta proposta) {
        LoggerUtils.logRequestStart(LOGGER, "salvarProposta", proposta);
        String sql = "CALL criar_proposta(?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql,
                    proposta.getFreelancerId(),
                    proposta.getProjetoId(),
                    proposta.getValor(),
                    proposta.getDataCriacao(),
                    proposta.getStatusProposta().toString(),
                    proposta.getObservacao()
            );
            LoggerUtils.logElapsedTime(LOGGER, "salvarProposta", System.currentTimeMillis());
        } catch (DataAccessException e) {
            LoggerUtils.logError(LOGGER, "salvarProposta", proposta, e);
            GlobalExceptionHandler.handleException(e);
        }
    }

    @Override
    public void avaliarEmpresa(Avaliacao avaliacao) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarEmpresa", avaliacao);
        try {
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
        } catch (DataAccessException e) {
            LoggerUtils.logError(LOGGER, "avaliarEmpresa", avaliacao, e);
            GlobalExceptionHandler.handleException(e);
        }
    }

    public List<ResponseEmpresaDto> listarEmpresas() {
        String sql = "SELECT * FROM listar_empresas()";

        try {
            return jdbcTemplate.query(sql, new EmpresaDtoRowMapper());
        } catch (DataAccessException e) {
            GlobalExceptionHandler.handleException(e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Projeto> listarTodosProjetos() {
        String sql = "SELECT * FROM listar_todos_projetos()";

        try {
            return jdbcTemplate.query(sql, new ProjetoDtoRowMapper());
        } catch (DataAccessException e) {
            GlobalExceptionHandler.handleException(e);
            return Collections.emptyList();
        }
    }

    @Override
    public ResponseEmpresaCompletaDto obterDetalhesEmpresa(Integer empresaId) {
        String sql = "SELECT * FROM obter_detalhes_empresa(?)";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{empresaId}, new EmpresaCompletaDtoRowMapper());
        } catch (DataAccessException e) {
            GlobalExceptionHandler.handleException(e);
            return null;
        }
    }

    @Override
    public List<ProjetoCompatibilidadeDto> buscarProjetosCompativeis(int idFreelancer) {
        LoggerUtils.logRequestStart(LOGGER, "buscarProjetosCompativeis", idFreelancer);
        String sql = "SELECT * FROM buscar_projetos_compatíveis(?)";

        try {
            return jdbcTemplate.query(sql, new Object[]{idFreelancer}, new ProjetoCompatibilidadeDtoRowMapper());
        } catch (DataAccessException e) {
            LoggerUtils.logError(LOGGER, "buscarProjetosCompativeis", idFreelancer, e);
            GlobalExceptionHandler.handleException(e);
            return Collections.emptyList();
        }
    }

    public void atualizarDadosFreelancer(RequestAtualizarFreelancerDto request) {
        LoggerUtils.logRequestStart(LOGGER, "atualizarDadosFreelancer", request);
        String sql = "CALL atualizar_freelancer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
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
        } catch (DataAccessException e) {
            LoggerUtils.logError(LOGGER, "atualizarDadosFreelancer", request, e);
            GlobalExceptionHandler.handleException(e);
        }
    }
}
