package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.IEmpresaJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.FreelancerDtoRowMapper;
import br.com.plataformafreelancer.fourcamp.dto.ResponseFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.RequestAnalisarPropostaDto;
import br.com.plataformafreelancer.fourcamp.handler.GlobalExceptionHandler;
import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Empresa;
import br.com.plataformafreelancer.fourcamp.model.Freelancer;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EmpresaJdbcTemplateDaoImpl implements IEmpresaJdbcTemplateDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaJdbcTemplateDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void salvarDadosCadastrais(Empresa empresa) {
        LOGGER.info("Início do método salvarDadosCadastrais com empresa: {}", empresa);
        try {
            String sql = "CALL cadastrar_empresa(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.update(sql,
                    empresa.getUsuario().getEmail(),
                    empresa.getUsuario().getSenha(),
                    empresa.getUsuario().getTipoUsuario().toString(),
                    empresa.getCnpj(),
                    empresa.getNome(),
                    empresa.getTelefone(),
                    empresa.getEndereco().getLogradouro(),
                    empresa.getEndereco().getNumero(),
                    empresa.getEndereco().getComplemento(),
                    empresa.getEndereco().getBairro(),
                    empresa.getEndereco().getCidade(),
                    empresa.getEndereco().getCep(),
                    empresa.getEndereco().getEstado(),
                    empresa.getEndereco().getPais(),
                    empresa.getNomeEmpresa(),
                    empresa.getRamoAtuacao(),
                    empresa.getSite()
            );
            LOGGER.info("Dados cadastrais da empresa salvos com sucesso: {}", empresa);
        } catch (DataAccessException e) {
            LOGGER.error("Erro ao salvar dados cadastrais da empresa: {}", empresa, e);
            GlobalExceptionHandler.handleException(e);
        }
    }

    @Override
    public void salvarDadosProjeto(Projeto projeto) {
        LOGGER.info("Início do método salvarDadosProjeto com projeto: {}", projeto);
        try {
            String sql = "CALL cadastrarProjeto(?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    projeto.getTitulo(),
                    projeto.getDescricao(),
                    projeto.getOrcamento(),
                    projeto.getPrazo(),
                    projeto.getStatusProjeto().toString(),
                    projeto.getDataCriacao(),
                    projeto.getEmpresaId(),
                    projeto.getHabilidades().toArray(new String[0])
            );
            LOGGER.info("Dados do projeto salvos com sucesso: {}", projeto);
        } catch (DataAccessException e) {
            LOGGER.error("Erro ao salvar dados do projeto: {}", projeto, e);
            GlobalExceptionHandler.handleException(e);
        }
    }

    @Override
    public void analisarProposta(RequestAnalisarPropostaDto request) {
        LOGGER.info("Início do método analisarProposta com request: {}", request);
        String sql = "CALL AtualizaStatusProposta(?, ?)";

        try {
            jdbcTemplate.update(sql,
                    request.getIdProposta(),
                    request.getStatusProposta().toString()
            );
            LOGGER.info("Proposta analisada com sucesso para o request: {}", request);
        } catch (DataAccessException e) {
            LOGGER.error("Erro ao analisar proposta: {}", request, e);
            GlobalExceptionHandler.handleException(e);
        }
    }

    @Override
    public void avaliarFreelancer(Avaliacao avaliacao) {
        LOGGER.info("Início do método avaliarFreelancer com avaliacao: {}", avaliacao);
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
            LOGGER.info("Avaliação do freelancer enviada com sucesso: {}", avaliacao);
        } catch (DataAccessException e) {
            LOGGER.error("Erro ao enviar avaliação do freelancer: {}", avaliacao, e);
            GlobalExceptionHandler.handleException(e);
        }
    }

    @Override
    public List<ResponseFreelancerDto> listarFreelacer() {
        String sql = "SELECT * FROM listar_freelancers()";

        try {
            return jdbcTemplate.query(sql, new FreelancerDtoRowMapper());
        } catch (DataAccessException e) {
            GlobalExceptionHandler.handleException(e);
            return Collections.emptyList();
        }
    }


}
