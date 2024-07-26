package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.FreelancerJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.EnderecoDto;
import br.com.plataformafreelancer.fourcamp.dtos.RequestAvaliacaoDto;
import br.com.plataformafreelancer.fourcamp.dtos.RequestFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.RequestPropostaDto;
import br.com.plataformafreelancer.fourcamp.enuns.StatusFreelancer;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.model.*;
import br.com.plataformafreelancer.fourcamp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        LOGGER.info("Início do método salvarDadosCadastrais com request: {}", request);
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
            LOGGER.info("Dados cadastrais salvos com sucesso para o freelancer: {}", freelancer);
        } catch (Exception e) {
            LOGGER.error("Erro ao salvar dados cadastrais do freelancer: {}", request, e);
            throw e;
        }
    }

    public void salvarProposta(RequestPropostaDto request) {
        LOGGER.info("Início do método salvarProposta com request: {}", request);
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
            LOGGER.info("Proposta salva com sucesso: {}", proposta);
        } catch (Exception e) {
            LOGGER.error("Erro ao salvar proposta: {}", request, e);
            throw e;
        }
    }

    public void avaliarEmpresa(RequestAvaliacaoDto request) {
        LOGGER.info("Início do método avaliarEmpresa com request: {}", request);
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
            LOGGER.info("Avaliação enviada com sucesso: {}", avaliacao);
        } catch (Exception e) {
            LOGGER.error("Erro ao avaliar empresa: {}", request, e);
            throw e;
        }
    }
}
