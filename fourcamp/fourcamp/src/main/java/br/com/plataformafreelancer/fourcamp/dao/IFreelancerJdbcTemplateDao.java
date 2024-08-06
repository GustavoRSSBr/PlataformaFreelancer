package br.com.plataformafreelancer.fourcamp.dao;

import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseProjetoCompatibilidadeDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaDto;
import br.com.plataformafreelancer.fourcamp.model.Avaliacao;
import br.com.plataformafreelancer.fourcamp.model.Freelancer;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.Proposta;

import java.util.List;

public interface IFreelancerJdbcTemplateDao {

    public void salvarDadosCadastrais(Freelancer freelancer);

    public void salvarProposta(Proposta proposta);

    public  void avaliarEmpresa(Avaliacao avaliacao);

    public List<ResponseEmpresaDto> listarEmpresas();

    public List<Projeto> listarTodosProjetos();

    public ResponseEmpresaCompletaDto obterDetalhesEmpresa(Integer empresaId);

    List<ResponseProjetoCompatibilidadeDto> buscarProjetosCompativeis(int idFreelancer);
}
