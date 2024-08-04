package br.com.plataformafreelancer.fourcamp.usecase;

import br.com.plataformafreelancer.fourcamp.dao.impl.FreelancerJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.*;
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

public class FreelancerServiceTest {

    @Mock
    private FreelancerJdbcTemplateDaoImpl freelancerJdbcTemplateDaoImpl;

    @Mock
    private CepService cepService;

    @Mock
    private EmailService emailService;

    @Mock
    private SenhaService senhaService;

    @Mock
    private NomeService nomeService;

    @Mock
    private DataService dataService;

    @Mock
    private TelefoneService telefoneService;

    @Mock
    private CpfService cpfService;

    @InjectMocks
    private FreelancerService freelancerService;

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

        RequestFreelancerDto requestDto = new RequestFreelancerDto();
        requestDto.setEmail("freelancer@exemplo.com");
        requestDto.setSenha("Senha@123");
        requestDto.setCep("01000-000");
        requestDto.setCpf("12345678909");
        requestDto.setTelefone("11987654321");
        requestDto.setNome("Freelancer Exemplo");
        requestDto.setDescricao("Desenvolvedor Java");

        when(cepService.buscaEnderecoPor(requestDto.getCep())).thenReturn(enderecoDto);
        doNothing().when(emailService).validarEmail(requestDto.getEmail());
        doNothing().when(senhaService).validarSenha(requestDto.getSenha());
        doNothing().when(nomeService).validarNome(requestDto.getNome());
        doNothing().when(cpfService).validarCpf(requestDto.getCpf());
        doNothing().when(telefoneService).validarNumeroTelefone(requestDto.getTelefone());
        when(dataService.converterParaLocalDate(requestDto.getDataNascimento())).thenReturn(null);
        when(dataService.coletarDataHoraAtual()).thenReturn("2023-01-01");

        freelancerService.salvarDadosCadastrais(requestDto);

        verify(freelancerJdbcTemplateDaoImpl).salvarDadosCadastrais(any(Freelancer.class));
    }

    @Test
    public void testSalvarProposta() {
        RequestPropostaDto requestDto = new RequestPropostaDto();
        requestDto.setFreelancerId(1);
        requestDto.setProjetoId(2);
        requestDto.setValor("5000");
        requestDto.setObservacao("Proposta para o projeto");

        when(dataService.coletarDataHoraAtual()).thenReturn("2023-01-01");

        freelancerService.salvarProposta(requestDto);

        verify(freelancerJdbcTemplateDaoImpl).salvarProposta(any(Proposta.class));
    }

    @Test
    public void testAvaliarEmpresa() {
        RequestAvaliacaoDto requestDto = new RequestAvaliacaoDto();
        requestDto.setEmpresaId(1);
        requestDto.setFreelancerId(2);
        requestDto.setProjetoId(3);
        requestDto.setComentario("Boa empresa");
        requestDto.setNota(5);

        when(dataService.coletarDataHoraAtual()).thenReturn("2023-01-01");

        freelancerService.avaliarEmpresa(requestDto);

        verify(freelancerJdbcTemplateDaoImpl).avaliarEmpresa(any(Avaliacao.class));
    }

    @Test
    public void testListarEmpresa() {
        ResponseEmpresaDto empresa1 = new ResponseEmpresaDto();
        ResponseEmpresaDto empresa2 = new ResponseEmpresaDto();
        List<ResponseEmpresaDto> empresas = Arrays.asList(empresa1, empresa2);

        when(freelancerJdbcTemplateDaoImpl.listarEmpresas()).thenReturn(empresas);

        List<ResponseEmpresaDto> result = freelancerService.listarEmpresa();

        assertEquals(2, result.size());
        verify(freelancerJdbcTemplateDaoImpl).listarEmpresas();
    }

    @Test
    public void testListarEmpresaNotFound() {
        when(freelancerJdbcTemplateDaoImpl.listarEmpresas()).thenReturn(null);

        assertThrows(NaoEncontradoException.class, () -> {
            freelancerService.listarEmpresa();
        });
    }

    @Test
    public void testListarTodosProjetos() {
        Projeto projeto1 = new Projeto();
        Projeto projeto2 = new Projeto();
        List<Projeto> projetos = Arrays.asList(projeto1, projeto2);

        when(freelancerJdbcTemplateDaoImpl.listarTodosProjetos()).thenReturn(projetos);

        List<Projeto> result = freelancerService.listarTodosProjetos();

        assertEquals(2, result.size());
        verify(freelancerJdbcTemplateDaoImpl).listarTodosProjetos();
    }

    @Test
    public void testListarTodosProjetosNotFound() {
        when(freelancerJdbcTemplateDaoImpl.listarTodosProjetos()).thenReturn(null);

        assertThrows(NaoEncontradoException.class, () -> {
            freelancerService.listarTodosProjetos();
        });
    }

    @Test
    public void testObterDetalhesEmpresa() {
        ResponseEmpresaCompletaDto empresa = new ResponseEmpresaCompletaDto();
        when(freelancerJdbcTemplateDaoImpl.obterDetalhesEmpresa(anyInt())).thenReturn(empresa);

        ResponseEmpresaCompletaDto result = freelancerService.obterDetalhesEmpresa(1);

        assertNotNull(result);
        verify(freelancerJdbcTemplateDaoImpl).obterDetalhesEmpresa(anyInt());
    }

    @Test
    public void testObterDetalhesEmpresaNotFound() {
        when(freelancerJdbcTemplateDaoImpl.obterDetalhesEmpresa(anyInt())).thenReturn(null);

        assertThrows(NaoEncontradoException.class, () -> {
            freelancerService.obterDetalhesEmpresa(1);
        });
    }

    @Test
    public void testListaProjetosCompativeis() {
        ProjetoCompatibilidadeDto projeto1 = new ProjetoCompatibilidadeDto();
        ProjetoCompatibilidadeDto projeto2 = new ProjetoCompatibilidadeDto();
        List<ProjetoCompatibilidadeDto> projetos = Arrays.asList(projeto1, projeto2);

        when(freelancerJdbcTemplateDaoImpl.buscarProjetosCompativeis(anyInt())).thenReturn(projetos);

        List<ProjetoCompatibilidadeDto> result = freelancerService.listaProjetosCompativeis(1);

        assertEquals(2, result.size());
        verify(freelancerJdbcTemplateDaoImpl).buscarProjetosCompativeis(anyInt());
    }

    @Test
    public void testListaProjetosCompativeisNotFound() {
        when(freelancerJdbcTemplateDaoImpl.buscarProjetosCompativeis(anyInt())).thenReturn(null);

        assertThrows(NaoEncontradoException.class, () -> {
            freelancerService.listaProjetosCompativeis(1);
        });
    }

    @Test
    public void testAtualizarDadosFreelancer() {
        RequestAtualizarFreelancerDto freelancerDto = new RequestAtualizarFreelancerDto();
        freelancerDto.setTelefone("11987654321");
        freelancerDto.setCep("01000-000");

        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setLogradouro("Rua Exemplo");
        enderecoDto.setBairro("Centro");
        enderecoDto.setLocalidade("São Paulo");
        enderecoDto.setCep("01000-000");
        enderecoDto.setUf("SP");

        when(cepService.buscaEnderecoPor(freelancerDto.getCep())).thenReturn(enderecoDto);
        doNothing().when(telefoneService).validarNumeroTelefone(freelancerDto.getTelefone());

        freelancerService.atualizarDadosFreelancer(freelancerDto);

        verify(freelancerJdbcTemplateDaoImpl).atualizarDadosFreelancer(any(RequestAtualizarFreelancerDto.class));
    }
}
