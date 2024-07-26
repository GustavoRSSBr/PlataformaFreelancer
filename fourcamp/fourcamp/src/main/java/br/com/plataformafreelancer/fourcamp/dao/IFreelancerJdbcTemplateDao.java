package br.com.plataformafreelancer.fourcamp.dao;

import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Freelancer;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.Proposta;

public interface IFreelancerJdbcTemplateDao {

    public void salvarDadosCadastrais(Freelancer freelancer);

    public void salvarProposta(Proposta proposta);

    public  void avaliarEmpresa(Avaliacao avaliacao);

}
