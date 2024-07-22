package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.IEmpresaJdbcTemplateDao;
import br.com.plataformafreelancer.fourcamp.model.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmpresaJdbcTemplateDaoImpl implements IEmpresaJdbcTemplateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void salvarDadosCadastrais(Empresa empresa) {
        String sql = "CALL cadastrar_empresa(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
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
        } catch (DataIntegrityViolationException e) {
            // Verificar se a exceção foi causada por uma violação de unicidade
            if (e.getMessage().contains("duplicate key value") || e.getMessage().contains("duplicar valor")) {
                throw new IllegalArgumentException("Email ou CNPJ já existente, digite outro.");
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

