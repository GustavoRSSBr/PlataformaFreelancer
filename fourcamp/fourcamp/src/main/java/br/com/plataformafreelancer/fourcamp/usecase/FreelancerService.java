package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.FreelancerJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAtualizarFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAvaliacaoDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestPropostaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEnderecoDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseProjetoCompatibilidadeDto;
import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.enuns.StatusFreelancer;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.exceptions.NaoEncontradoException;
import br.com.plataformafreelancer.fourcamp.model.*;
import br.com.plataformafreelancer.fourcamp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreelancerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FreelancerService.class);

    @Autowired
    private CepService cepService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SenhaService senhaService;

    @Autowired
    private NomeService nomeService;

    @Autowired
    private DataService dataService;

    @Autowired
    private TelefoneService telefoneService;

    @Autowired
    private CpfService cpfService;

    @Autowired
    private FreelancerJdbcTemplateDaoImpl freelancerJdbcTemplateDaoImpl;

    public void salvarDadosCadastrais(RequestFreelancerDto request) {
        LoggerUtils.logRequestStart(LOGGER, "salvarDadosCadastrais", request);

        emailService.validarEmail(request.getEmail());
        senhaService.validarSenha(request.getSenha());
        nomeService.validarNome(request.getNome());
        cpfService.validarCpf(request.getCpf());
        dataService.validarDataNascimento(request.getDataNascimento());
        telefoneService.validarNumeroTelefone(request.getTelefone());

        ResponseEnderecoDto responseEnderecoDto = cepService.buscaEnderecoPor(request.getCep());

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .senha(request.getSenha())
                .tipoUsuario(TipoUsuario.FREELANCER)
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

        Freelancer freelancer = Freelancer.builder()
                .usuario(usuario)
                .nome(request.getNome())
                .cpf(request.getCpf())
                .dataNascimento(dataService.converterParaLocalDate(request.getDataNascimento()))
                .telefone(request.getTelefone())
                .endereco(endereco)
                .descricao(request.getDescricao())
                .disponibilidade(request.getDisponibilidade())
                .dataCriacao(dataService.coletarDataHoraAtual())
                .statusFreelancer(StatusFreelancer.ATIVO)
                .habilidades(request.getHabilidades())
                .build();

        freelancerJdbcTemplateDaoImpl.salvarDadosCadastrais(freelancer);
        LoggerUtils.logElapsedTime(LOGGER, "salvarDadosCadastrais", System.currentTimeMillis());
    }

    public void salvarProposta(RequestPropostaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "salvarProposta", request);

        Proposta proposta = Proposta.builder()
                .freelancerId(request.getFreelancerId())
                .projetoId(request.getProjetoId())
                .valor(request.getValor())
                .observacao(request.getObservacao())
                .statusProposta(StatusProposta.PENDENTE)
                .dataCriacao(dataService.coletarDataHoraAtual())
                .build();

        freelancerJdbcTemplateDaoImpl.salvarProposta(proposta);
        LoggerUtils.logElapsedTime(LOGGER, "salvarProposta", System.currentTimeMillis());
    }

    public void avaliarEmpresa(RequestAvaliacaoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarEmpresa", request);

        Avaliacao avaliacao = Avaliacao.builder()
                .empresaId(request.getEmpresaId())
                .freelancerId(request.getFreelancerId())
                .projetoId(request.getProjetoId())
                .comentario(request.getComentario())
                .avaliado(TipoUsuario.EMPRESA)
                .nota(request.getNota())
                .dataAvaliacao(dataService.coletarDataHoraAtual())
                .build();

        freelancerJdbcTemplateDaoImpl.avaliarEmpresa(avaliacao);
        LoggerUtils.logElapsedTime(LOGGER, "avaliarEmpresa", System.currentTimeMillis());
    }

    public List<ResponseEmpresaDto> listarEmpresa() {
        List<ResponseEmpresaDto> lista = freelancerJdbcTemplateDaoImpl.listarEmpresas();
        if (lista == null || lista.isEmpty()) {
            throw new NaoEncontradoException(ErrorCode.LISTA_VAZIA.getCustomMessage());
        }
        return lista;
    }

    public List<Projeto> listarTodosProjetos() {
        List<Projeto> lista = freelancerJdbcTemplateDaoImpl.listarTodosProjetos();
        if (lista == null || lista.isEmpty()) {
            throw new NaoEncontradoException(ErrorCode.LISTA_VAZIA.getCustomMessage());
        }
        return lista;
    }

    public ResponseEmpresaCompletaDto obterDetalhesEmpresa(Integer empresaId) {
        ResponseEmpresaCompletaDto empresa = freelancerJdbcTemplateDaoImpl.obterDetalhesEmpresa(empresaId);
        if (empresa == null) {
            throw new NaoEncontradoException(ErrorCode.OBJETO_VAZIO.getCustomMessage());
        }
        return empresa;
    }

    public List<ResponseProjetoCompatibilidadeDto> listaProjetosCompativeis(Integer id) {
        List<ResponseProjetoCompatibilidadeDto> lista = freelancerJdbcTemplateDaoImpl.buscarProjetosCompativeis(id);
        if (lista == null || lista.isEmpty()) {
            throw new NaoEncontradoException(ErrorCode.LISTA_VAZIA.getCustomMessage());
        }
        return lista;
    }

    public void atualizarDadosFreelancer(RequestAtualizarFreelancerDto freelancer) {
        telefoneService.validarNumeroTelefone(freelancer.getTelefone());

        ResponseEnderecoDto endereco = cepService.buscaEnderecoPor(freelancer.getCep());

        freelancer.setBairro(endereco.getBairro());
        freelancer.setCidade(endereco.getLocalidade());
        freelancer.setEstado(endereco.getUf());
        freelancer.setLogradouro(endereco.getLogradouro());

        freelancerJdbcTemplateDaoImpl.atualizarDadosFreelancer(freelancer);
    }
}
