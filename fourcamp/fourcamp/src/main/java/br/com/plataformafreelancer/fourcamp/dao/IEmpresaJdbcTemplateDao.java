package br.com.plataformafreelancer.fourcamp.dao;
import br.com.plataformafreelancer.fourcamp.dtos.RequestAnalisarPropostaDto;
import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Empresa;
import br.com.plataformafreelancer.fourcamp.model.Projeto;

public interface IEmpresaJdbcTemplateDao {
    public void salvarDadosCadastrais(Empresa empresa);

    public void salvarDadosProjeto(Projeto projeto);

    public void analisarProposta(RequestAnalisarPropostaDto request);

    public  void avaliarFreelancer(Avaliacao avaliacao);
}
