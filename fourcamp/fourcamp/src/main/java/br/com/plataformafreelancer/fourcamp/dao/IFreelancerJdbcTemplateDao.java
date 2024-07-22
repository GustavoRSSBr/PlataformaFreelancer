package br.com.plataformafreelancer.fourcamp.dao;

import br.com.plataformafreelancer.fourcamp.model.Freelancer;

public interface IFreelancerJdbcTemplateDao {

    public void salvarDadosCadastrais(Freelancer freelancer);
}
