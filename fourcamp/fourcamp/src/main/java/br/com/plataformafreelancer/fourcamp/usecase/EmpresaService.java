package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.EmpresaJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.*;
import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProjeto;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.exceptions.ListaException;
import br.com.plataformafreelancer.fourcamp.model.*;
import br.com.plataformafreelancer.fourcamp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.plataformafreelancer.fourcamp.dto.ResponseFreelancerDto;

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
        LOGGER.info("Início do método salvarDadosCadastrais com request: {}", request);
        try {
            emailService.validarEmail(request.getEmail());
            senhaService.validarSenha(request.getSenha());
            EnderecoDto enderecoDto = cepService.buscaEnderecoPor(request.getCep());
            cnpjService.validarCnpj(request.getCnpj());
            telefoneService.validarNumeroTelefone(request.getTelefone());

            Usuario usuario = Usuario.builder()
                    .email(request.getEmail())
                    .senha(request.getSenha())
                    .tipoUsuario(TipoUsuario.EMPRESA)
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
            LOGGER.info("Dados cadastrais salvos com sucesso para a empresa: {}", empresa);
        } catch (Exception e) {
            LOGGER.error("Erro ao salvar dados cadastrais da empresa: {}", request, e);
            throw e;
        }
    }

    public void salvarDadosProjeto(RequestProjetoDto request) {
        LOGGER.info("Início do método salvarDadosProjeto com request: {}", request);
        try {
            Projeto projeto = Projeto.builder()
                    .titulo(request.getTitulo())
                    .descricao(request.getDescricao())
                    .orcamento(request.getOrçamento())
                    .prazo(request.getPrazo())
                    .empresaId(request.getIdEmpresa())
                    .habilidades(request.getHabilidades())
                    .statusProjeto(StatusProjeto.ATIVO)
                    .dataCriacao(dataService.coletarDataHoraAtual())
                    .build();

            empresaJdbcTemplateDao.salvarDadosProjeto(projeto);
            LOGGER.info("Dados do projeto salvos com sucesso: {}", projeto);
        } catch (Exception e) {
            LOGGER.error("Erro ao salvar dados do projeto: {}", request, e);
            throw e;
        }
    }

    public void analisarProposta(RequestAnalisarPropostaDto request) {
        LOGGER.info("Início do método analisarProposta com request: {}", request);
        try {
            empresaJdbcTemplateDao.analisarProposta(request);
            LOGGER.info("Proposta analisada com sucesso para o request: {}", request);
        } catch (Exception e) {
            LOGGER.error("Erro ao analisar proposta: {}", request, e);
            throw e;
        }
    }

    public void avaliarFreelancer(RequestAvaliacaoDto request) {
        LOGGER.info("Início do método avaliarFreelancer com request: {}", request);
        try {
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
            LOGGER.info("Avaliação enviada com sucesso: {}", avaliacao);
        } catch (Exception e) {
            LOGGER.error("Erro ao avaliar freelancer: {}", request, e);
            throw e;
        }
    }

    public List<ResponseFreelancerDto> listarFreelancer() {

       List<ResponseFreelancerDto> lista = empresaJdbcTemplateDao.listarFreelacer();
       if(lista == null || lista.isEmpty()){
           throw new ListaException(ErrorCode.LISTA_VAZIA.getCustomMessage());
       }

       return lista;
    }
}
