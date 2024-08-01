package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.FreelancerJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.*;
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
        try {
            emailService.validarEmail(request.getEmail());
            senhaService.validarSenha(request.getSenha());
            nomeService.validarNome(request.getNome());
            cpfService.validarCpf(request.getCpf());
            dataService.validarDataNascimento(request.getDataNascimento());
            telefoneService.validarNumeroTelefone(request.getTelefone());

            EnderecoDto enderecoDto = cepService.buscaEnderecoPor(request.getCep());

            Usuario usuario = Usuario.builder()
                    .email(request.getEmail())
                    .senha(request.getSenha())
                    .tipoUsuario(TipoUsuario.FREELANCER)
                    .build();

            Endereco endereco = Endereco.builder()
                    .logradouro(enderecoDto.getLogradouro())
                    .numero(request.getNumero())
                    .complemento(request.getComplemento())
                    .bairro(enderecoDto.getBairro())
                    .cidade(enderecoDto.getLocalidade())
                    .cep(enderecoDto.getCep())
                    .estado(enderecoDto.getUf())
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
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "salvarDadosCadastrais", request, e);
            throw e;
        }
    }

    public void salvarProposta(RequestPropostaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "salvarProposta", request);
        try {
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
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "salvarProposta", request, e);
            throw e;
        }
    }

    public void avaliarEmpresa(RequestAvaliacaoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarEmpresa", request);
        try {
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
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "avaliarEmpresa", request, e);
            throw e;
        }
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

    public List<ProjetoCompatibilidadeDto> listaProjetosCompativeis(Integer id) {
        List<ProjetoCompatibilidadeDto> lista = freelancerJdbcTemplateDaoImpl.buscarProjetosCompativeis(id);
        if (lista == null || lista.isEmpty()) {
            throw new NaoEncontradoException(ErrorCode.LISTA_VAZIA.getCustomMessage());
        }
        return lista;
    }

    public void atualizarDadosFreelancer(RequestAtualizarFreelancerDto freelancer) {
        telefoneService.validarNumeroTelefone(freelancer.getTelefone());

        EnderecoDto endereco = cepService.buscaEnderecoPor(freelancer.getCep());

        freelancer.setBairro(endereco.getBairro());
        freelancer.setCidade(endereco.getLocalidade());
        freelancer.setEstado(endereco.getUf());
        freelancer.setLogradouro(endereco.getLogradouro());

        freelancerJdbcTemplateDaoImpl.atualizarDadosFreelancer(freelancer);
    }
}
