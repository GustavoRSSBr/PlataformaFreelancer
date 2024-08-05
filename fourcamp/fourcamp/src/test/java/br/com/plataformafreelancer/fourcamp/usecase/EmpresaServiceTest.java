package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.EmpresaJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.*;
import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import br.com.plataformafreelancer.fourcamp.exceptions.NaoEncontradoException;
import br.com.plataformafreelancer.fourcamp.model.*;
import br.com.plataformafreelancer.fourcamp.utils.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class EmpresaServiceTest {

    @Mock
    private EmpresaJdbcTemplateDaoImpl empresaJdbcTemplateDao;

    @Mock
    private EmailService emailService;

    @Mock
    private SenhaService senhaService;

    @Mock
    private CepService cepService;

    @Mock
    private CnpjService cnpjService;

    @Mock
    private TelefoneService telefoneService;

    @Mock
    private DataService dataService;

    @InjectMocks
    private EmpresaService empresaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarDadosCadastrais() {
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setLogradouro("Rua Exemplo");
        enderecoDto.setBairro("Centro");
        enderecoDto.setLocalidade("São Paulo");
        enderecoDto.setCep("01000-000");
        enderecoDto.setUf("SP");

        RequestEmpresaDto requestDto = new RequestEmpresaDto();
        requestDto.setEmail("email@exemplo.com");
        requestDto.setSenha("Senha@123");
        requestDto.setCep("01000-000");
        requestDto.setCnpj("12345678000199");
        requestDto.setTelefone("11987654321");
        requestDto.setNome("Empresa Exemplo");
        requestDto.setNomeEmpresa("Empresa X");
        requestDto.setRamoAtuacao("Tecnologia");
        requestDto.setSite("www.empresaexemplo.com");
        requestDto.setNumero("123");
        requestDto.setComplemento("Sala 1");
        requestDto.setPais("Brasil");

        when(cepService.buscaEnderecoPor(requestDto.getCep())).thenReturn(enderecoDto);
        doNothing().when(emailService).validarEmail(requestDto.getEmail());
        doNothing().when(senhaService).validarSenha(requestDto.getSenha());
        doNothing().when(cnpjService).validarCnpj(requestDto.getCnpj());
        doNothing().when(telefoneService).validarNumeroTelefone(requestDto.getTelefone());

        empresaService.salvarDadosCadastrais(requestDto);

        verify(empresaJdbcTemplateDao).salvarDadosCadastrais(any(Empresa.class));
    }

    @Test
    public void testSalvarDadosProjeto() {
        RequestProjetoDto requestDto = new RequestProjetoDto();
        requestDto.setTitulo("Projeto X");
        requestDto.setDescricao("Descrição do projeto X");
        requestDto.setOrcamento("5000");
        requestDto.setPrazo("2023-12-31");
        requestDto.setIdEmpresa(1);

        when(dataService.coletarDataHoraAtual()).thenReturn("2023-01-01");

        empresaService.salvarDadosProjeto(requestDto);

        verify(empresaJdbcTemplateDao).salvarDadosProjeto(any(Projeto.class));
    }

    @Test
    public void testAnalisarProposta() {
        RequestAnalisarPropostaDto requestDto = new RequestAnalisarPropostaDto();
        requestDto.setIdProposta(1);
        requestDto.setStatusProposta(StatusProposta.ACEITA);

        empresaService.analisarProposta(requestDto);

        verify(empresaJdbcTemplateDao).analisarProposta(any(RequestAnalisarPropostaDto.class));
    }

    @Test
    public void testAvaliarFreelancer() {
        RequestAvaliacaoDto requestDto = new RequestAvaliacaoDto();
        requestDto.setEmpresaId(1);
        requestDto.setFreelancerId(2);
        requestDto.setProjetoId(3);
        requestDto.setComentario("Bom trabalho");
        requestDto.setNota(5);

        when(dataService.coletarDataHoraAtual()).thenReturn("2023-01-01");

        empresaService.avaliarFreelancer(requestDto);

        verify(empresaJdbcTemplateDao).avaliarFreelancer(any(Avaliacao.class));
    }

    @Test
    public void testListarFreelancer() {
        ResponseFreelancerDto freelancer1 = new ResponseFreelancerDto();
        ResponseFreelancerDto freelancer2 = new ResponseFreelancerDto();
        List<ResponseFreelancerDto> freelancers = Arrays.asList(freelancer1, freelancer2);

        when(empresaJdbcTemplateDao.listarFreelacer()).thenReturn(freelancers);

        List<ResponseFreelancerDto> result = empresaService.listarFreelancer();

        assertEquals(2, result.size());
        verify(empresaJdbcTemplateDao).listarFreelacer();
    }

    @Test
    public void testListarFreelancerNotFound() {
        when(empresaJdbcTemplateDao.listarFreelacer()).thenReturn(null);

        assertThrows(NaoEncontradoException.class, () -> {
            empresaService.listarFreelancer();
        });
    }

    @Test
    public void testObterDetalhesFreelancer() {
        ResponseFreelancerCompletaDto freelancer = new ResponseFreelancerCompletaDto();
        when(empresaJdbcTemplateDao.obterDetalhesFreelancer(anyInt())).thenReturn(freelancer);

        ResponseFreelancerCompletaDto result = empresaService.obterDetalhesFreelancer(1);

        assertNotNull(result);
        verify(empresaJdbcTemplateDao).obterDetalhesFreelancer(anyInt());
    }

    @Test
    public void testObterDetalhesFreelancerNotFound() {
        when(empresaJdbcTemplateDao.obterDetalhesFreelancer(anyInt())).thenReturn(null);

        assertThrows(NaoEncontradoException.class, () -> {
            empresaService.obterDetalhesFreelancer(1);
        });
    }

    @Test
    public void testListarPropostasPorProjeto() {
        ResponsePropostaDto proposta1 = new ResponsePropostaDto();
        ResponsePropostaDto proposta2 = new ResponsePropostaDto();
        List<ResponsePropostaDto> propostas = Arrays.asList(proposta1, proposta2);

        when(empresaJdbcTemplateDao.listarPropostasPorProjeto(anyInt())).thenReturn(propostas);

        List<ResponsePropostaDto> result = empresaService.listarPropostasPorProjeto(1);

        assertEquals(2, result.size());
        verify(empresaJdbcTemplateDao).listarPropostasPorProjeto(anyInt());
    }

    @Test
    public void testListarPropostasPorProjetoNotFound() {
        when(empresaJdbcTemplateDao.listarPropostasPorProjeto(anyInt())).thenReturn(null);

        assertThrows(NaoEncontradoException.class, () -> {
            empresaService.listarPropostasPorProjeto(1);
        });
    }

    @Test
    public void testAtualizarDadosEmpresa() {
        RequestAtualizarEmpresaDto empresaDto = new RequestAtualizarEmpresaDto();
        empresaDto.setIdEmpresa(1);
        empresaDto.setNome("Empresa Atualizada");
        empresaDto.setTelefone("11987654321");
        empresaDto.setRamoAtuacao("Tecnologia");
        empresaDto.setSite("www.empresaatualizada.com");

        doNothing().when(telefoneService).validarNumeroTelefone(anyString());

        empresaService.atualizarDadosEmpresa(empresaDto);

        verify(empresaJdbcTemplateDao).atualizarDadosEmpresa(any(RequestAtualizarEmpresaDto.class));
    }

    @Test
    public void testAtualizarDadosProjeto() {
        RequestAtualizarProjetoDto projetoDto = new RequestAtualizarProjetoDto();
        projetoDto.setIdProjeto(1);
        projetoDto.setTitulo("Novo Sistema");
        projetoDto.setDescricao("Desenvolvimento de um novo sistema de gestão");
        projetoDto.setOrcamento("10000.00");
        projetoDto.setPrazo("2024-01-01");
        projetoDto.setHabilidades(Arrays.asList("Java", "Spring", "MySQL"));

        empresaService.atualizarDadosProjeto(projetoDto);

        verify(empresaJdbcTemplateDao).atualizarProjeto(any(RequestAtualizarProjetoDto.class));
    }

    @Test
    public void testExcluirProjetoSeNaoAssociado() {
        doNothing().when(empresaJdbcTemplateDao).excluirProjetoSeNaoAssociado(anyInt());

        empresaService.excluirProjetoSeNaoAssociado(1);

        verify(empresaJdbcTemplateDao).excluirProjetoSeNaoAssociado(anyInt());
    }

    @Test
    public void testExcluirProjetoSeNaoAssociadoNotFound() {
        doThrow(new NaoEncontradoException(ErrorCode.OBJETO_VAZIO.getCustomMessage()))
                .when(empresaJdbcTemplateDao).excluirProjetoSeNaoAssociado(anyInt());

        assertThrows(NaoEncontradoException.class, () -> {
            empresaService.excluirProjetoSeNaoAssociado(1);
        });
    }
}
