package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.EmpresaJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.*;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEnderecoDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseFreelancerCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponsePropostaDto;
import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProjeto;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.exceptions.NaoEncontradoException;
import br.com.plataformafreelancer.fourcamp.model.*;
import br.com.plataformafreelancer.fourcamp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseFreelancerDto;

import java.util.List;

@Service
public class EmpresaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaService.class);

    @Autowired
    EmailService emailService;

    @Autowired
    SenhaService senhaService;

    @Autowired
    CepService cepService;

    @Autowired
    CnpjService cnpjService;

    @Autowired
    TelefoneService telefoneService;

    @Autowired
    DataService dataService;

    @Autowired
    EmpresaJdbcTemplateDaoImpl empresaJdbcTemplateDao;

    public void salvarDadosCadastrais(RequestEmpresaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "salvarDadosCadastrais", request);

        emailService.validarEmail(request.getEmail());
        senhaService.validarSenha(request.getSenha());
        ResponseEnderecoDto responseEnderecoDto = cepService.buscaEnderecoPor(request.getCep());
        cnpjService.validarCnpj(request.getCnpj());
        telefoneService.validarNumeroTelefone(request.getTelefone());

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .senha(request.getSenha())
                .tipoUsuario(TipoUsuario.EMPRESA)
                .build();

        Endereco endereco = Endereco.builder()
                .logradouro(responseEnderecoDto.getLogradouro())
                .numero(request.getNumero())
                .complemento(request.getComplemento())
                .bairro(responseEnderecoDto.getBairro())
                .cidade(responseEnderecoDto.getLocalidade())
                .cep(responseEnderecoDto.getCep())
                .estado(responseEnderecoDto.getUf())
                .pais(request.getPais())
                .build();

        Empresa empresa = Empresa.builder()
                .usuario(usuario)
                .cnpj(request.getCnpj())
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .endereco(endereco)
                .nomeEmpresa(request.getNomeEmpresa())
                .ramoAtuacao(request.getRamoAtuacao())
                .site(request.getSite())
                .build();

        empresaJdbcTemplateDao.salvarDadosCadastrais(empresa);
        LoggerUtils.logElapsedTime(LOGGER, "salvarDadosCadastrais", System.currentTimeMillis());
    }

    public void salvarDadosProjeto(RequestProjetoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "salvarDadosProjeto", request);

        Projeto projeto = Projeto.builder()
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .orcamento(request.getOrcamento())
                .prazo(request.getPrazo())
                .empresaId(request.getIdEmpresa())
                .habilidades(request.getHabilidades())
                .statusProjeto(StatusProjeto.ATIVO)
                .dataCriacao(dataService.coletarDataHoraAtual())
                .build();

        empresaJdbcTemplateDao.salvarDadosProjeto(projeto);
        LoggerUtils.logElapsedTime(LOGGER, "salvarDadosProjeto", System.currentTimeMillis());
    }

    public void analisarProposta(RequestAnalisarPropostaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "analisarProposta", request);

        empresaJdbcTemplateDao.analisarProposta(request);
        LoggerUtils.logElapsedTime(LOGGER, "analisarProposta", System.currentTimeMillis());
    }

    public void avaliarFreelancer(RequestAvaliacaoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarFreelancer", request);

        Avaliacao avaliacao = Avaliacao.builder()
                .empresaId(request.getEmpresaId())
                .freelancerId(request.getFreelancerId())
                .projetoId(request.getProjetoId())
                .comentario(request.getComentario())
                .avaliado(TipoUsuario.FREELANCER)
                .nota(request.getNota())
                .dataAvaliacao(dataService.coletarDataHoraAtual())
                .build();

        empresaJdbcTemplateDao.avaliarFreelancer(avaliacao);
        LoggerUtils.logElapsedTime(LOGGER, "avaliarFreelancer", System.currentTimeMillis());
    }

    public List<ResponseFreelancerDto> listarFreelancer() {
        List<ResponseFreelancerDto> lista = empresaJdbcTemplateDao.listarFreelacer();
        if (lista == null || lista.isEmpty()) {
            throw new NaoEncontradoException(ErrorCode.LISTA_VAZIA.getCustomMessage());
        }

        return lista;
    }

    public ResponseFreelancerCompletaDto obterDetalhesFreelancer(Integer freelancerID) {
        ResponseFreelancerCompletaDto freelancer = empresaJdbcTemplateDao.obterDetalhesFreelancer(freelancerID);
        if (freelancer == null) {
            throw new NaoEncontradoException(ErrorCode.OBJETO_VAZIO.getCustomMessage());
        }
        return freelancer;
    }

    public List<ResponsePropostaDto> listarPropostasPorProjeto(Integer projetoId) {
        List<ResponsePropostaDto> propostas = empresaJdbcTemplateDao.listarPropostasPorProjeto(projetoId);
        if (propostas == null || propostas.isEmpty()) {
            throw new NaoEncontradoException(ErrorCode.LISTA_VAZIA.getCustomMessage());
        }
        return propostas;
    }

    public void atualizarDadosEmpresa(RequestAtualizarEmpresaDto empresa) {
        telefoneService.validarNumeroTelefone(empresa.getTelefone());

        empresaJdbcTemplateDao.atualizarDadosEmpresa(empresa);
    }

    public void atualizarDadosProjeto(RequestAtualizarProjetoDto projeto) {
        empresaJdbcTemplateDao.atualizarProjeto(projeto);
    }

    public void excluirProjetoSeNaoAssociado(Integer idProjeto) {
        empresaJdbcTemplateDao.excluirProjetoSeNaoAssociado(idProjeto);
    }
}
