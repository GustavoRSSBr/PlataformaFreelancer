package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.IFreelancerJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.model.Freelancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class FreelancerJdbcTemplateDaoImpl implements IFreelancerJdbcTemplateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void salvarDadosCadastrais(Freelancer freelancer) {
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
                    freelancer.getStatus().toString(),
                    freelancer.getHabilidades().toArray(new String[0])
            );
        } catch (DataIntegrityViolationException e) {
            // Verificar se a exceção foi causada por uma violação de unicidade
            if (e.getMessage().contains("duplicate key value") || e.getMessage().contains("duplicar valor")) {
                throw new IllegalArgumentException("Email ou CPF já existente, digite outro.");
            } else if (e.getMessage().contains("null value in column") || e.getMessage().contains("valor nulo na coluna")) {
                throw new IllegalArgumentException("Um valor obrigatório não foi fornecido. Verifique os campos e tente novamente.");
            } else {
                throw new RuntimeException("Ocorreu um erro de integridade de dados. Por favor, verifique os dados inseridos e tente novamente.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.");
        }
    }
}