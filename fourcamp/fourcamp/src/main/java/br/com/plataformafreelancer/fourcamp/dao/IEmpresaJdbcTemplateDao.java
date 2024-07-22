package br.com.plataformafreelancer.fourcamp.dao;
import br.com.plataformafreelancer.fourcamp.model.Empresa;

public interface IEmpresaJdbcTemplateDao {
    public void salvarDadosCadastrais(Empresa empresa);
}
