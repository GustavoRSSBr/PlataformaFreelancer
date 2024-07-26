package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.IFreelancerJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.handler.GlobalExceptionHandler;
import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Freelancer;
import br.com.plataformafreelancer.fourcamp.model.Proposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class FreelancerJdbcTemplateDaoImpl implements IFreelancerJdbcTemplateDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreelancerJdbcTemplateDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void salvarDadosCadastrais(Freelancer freelancer) {
        LOGGER.info("Início do método salvarDadosCadastrais com freelancer: {}", freelancer);
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
            LOGGER.info("Dados cadastrais do freelancer salvos com sucesso: {}", freelancer);
        } catch (DataAccessException e) {
            LOGGER.error("Erro ao salvar dados cadastrais do freelancer: {}", freelancer, e);
            GlobalExceptionHandler.handleException(e);
        }
    }

    @Override
    public void salvarProposta(Proposta proposta) {
        LOGGER.info("Início do método salvarProposta com proposta: {}", proposta);
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
            LOGGER.info("Proposta salva com sucesso: {}", proposta);
        } catch (DataAccessException e) {
            LOGGER.error("Erro ao salvar proposta: {}", proposta, e);
            GlobalExceptionHandler.handleException(e);
        }
    }

    @Override
    public void avaliarEmpresa(Avaliacao avaliacao) {
        LOGGER.info("Início do método avaliarEmpresa com avaliacao: {}", avaliacao);
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
            LOGGER.info("Avaliação da empresa enviada com sucesso: {}", avaliacao);
        } catch (DataAccessException e) {
            LOGGER.error("Erro ao enviar avaliação da empresa: {}", avaliacao, e);
            GlobalExceptionHandler.handleException(e);
        }
    }
}
