package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.*;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.FreelancerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FreelancerControllerTest {

    @Mock
    private FreelancerService freelancerService;

    @InjectMocks
    private FreelancerController freelancerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarFreelancer() {
        RequestFreelancerDto requestDto = new RequestFreelancerDto();
        doNothing().when(freelancerService).salvarDadosCadastrais(requestDto);

        ResponseEntity<?> response = freelancerController.cadastrarFreelancer(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        StandardResponse standardResponse = (StandardResponse) response.getBody();
        assertEquals("Freelancer cadastrado com sucesso!", standardResponse.getMessage());

        verify(freelancerService).salvarDadosCadastrais(requestDto);
    }

    @Test
    public void testEnviarProposta() {
        RequestPropostaDto requestDto = new RequestPropostaDto();
        doNothing().when(freelancerService).salvarProposta(requestDto);

        ResponseEntity<?> response = freelancerController.enviarProposta(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        StandardResponse standardResponse = (StandardResponse) response.getBody();
        assertEquals("Proposta enviada com sucesso!", standardResponse.getMessage());

        verify(freelancerService).salvarProposta(requestDto);
    }

    @Test
    public void testAvaliarEmpresa() {
        RequestAvaliacaoDto requestDto = new RequestAvaliacaoDto();
        doNothing().when(freelancerService).avaliarEmpresa(requestDto);

        ResponseEntity<?> response = freelancerController.avaliarEmpresa(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        StandardResponse standardResponse = (StandardResponse) response.getBody();
        assertEquals("Avaliação enviada com sucesso!", standardResponse.getMessage());

        verify(freelancerService).avaliarEmpresa(requestDto);
    }

    @Test
    public void testListaEmpresa() {
        ResponseEmpresaDto empresa1 = ResponseEmpresaDto.builder()
                .idEmpresa(1)
                .nome("Empresa One")
                .telefone("123456789")
                .ramoAtuacao("Tecnologia")
                .site("https://www.empresaone.com")
                .build();

        ResponseEmpresaDto empresa2 = ResponseEmpresaDto.builder()
                .idEmpresa(2)
                .nome("Empresa Two")
                .telefone("987654321")
                .ramoAtuacao("Design")
                .site("https://www.empresatwo.com")
                .build();

        List<ResponseEmpresaDto> empresasMock = Arrays.asList(empresa1, empresa2);

        when(freelancerService.listarEmpresa()).thenReturn(empresasMock);

        ResponseEntity<?> response = freelancerController.listaEmpresa();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ResponseEmpresaDto> responseBody = (List<ResponseEmpresaDto>) response.getBody();
        assertEquals(2, responseBody.size());
        assertEquals(empresa1, responseBody.get(0));
        assertEquals(empresa2, responseBody.get(1));

        verify(freelancerService).listarEmpresa();
    }

    @Test
    public void testListaProjetos() {
        Projeto projeto1 = Projeto.builder()
                .idProjeto(1)
                .titulo("Projeto One")
                .descricao("Descrição do Projeto One")
                .build();

        Projeto projeto2 = Projeto.builder()
                .idProjeto(2)
                .titulo("Projeto Two")
                .descricao("Descrição do Projeto Two")
                .build();

        List<Projeto> projetosMock = Arrays.asList(projeto1, projeto2);

        when(freelancerService.listarTodosProjetos()).thenReturn(projetosMock);

        ResponseEntity<?> response = freelancerController.listaProjetos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Projeto> responseBody = (List<Projeto>) response.getBody();
        assertEquals(2, responseBody.size());
        assertEquals(projeto1, responseBody.get(0));
        assertEquals(projeto2, responseBody.get(1));

        verify(freelancerService).listarTodosProjetos();
    }

    @Test
    public void testExibirDetalhesEmpresa() {
        Integer empresaId = 1;
        ResponseEmpresaCompletaDto empresaMock = ResponseEmpresaCompletaDto.builder()
                .idEmpresa(empresaId)
                .nome("Empresa One")
                .telefone("123456789")
                .ramoAtuacao("Tecnologia")
                .site("https://www.empresaone.com")
                .build();

        when(freelancerService.obterDetalhesEmpresa(empresaId)).thenReturn(empresaMock);

        ResponseEntity<?> response = freelancerController.exibirDetalhesEmpresa(empresaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponseEmpresaCompletaDto responseBody = (ResponseEmpresaCompletaDto) response.getBody();
        assertEquals(empresaMock, responseBody);

        verify(freelancerService).obterDetalhesEmpresa(empresaId);
    }

    @Test
    public void testBuscarProjetoCompativel() {
        Integer freelancerId = 1;

        ProjetoCompatibilidadeDto projeto1 = ProjetoCompatibilidadeDto.builder()
                .idProjeto(1)
                .titulo("Projeto One")
                .descricao("Descrição do Projeto One")
                .build();

        ProjetoCompatibilidadeDto projeto2 = ProjetoCompatibilidadeDto.builder()
                .idProjeto(2)
                .titulo("Projeto Two")
                .descricao("Descrição do Projeto Two")
                .build();

        List<ProjetoCompatibilidadeDto> projetosMock = Arrays.asList(projeto1, projeto2);

        when(freelancerService.listaProjetosCompativeis(freelancerId)).thenReturn(projetosMock);

        ResponseEntity<?> response = freelancerController.buscarProjetoCompativel(freelancerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ProjetoCompatibilidadeDto> responseBody = (List<ProjetoCompatibilidadeDto>) response.getBody();
        assertEquals(2, responseBody.size());
        assertEquals(projeto1, responseBody.get(0));
        assertEquals(projeto2, responseBody.get(1));

        verify(freelancerService).listaProjetosCompativeis(freelancerId);
    }

    @Test
    public void testAtualizarFreelancer() {
        RequestAtualizarFreelancerDto requestDto = RequestAtualizarFreelancerDto.builder()
                .idFreelancer(1)
                .numero("Freelancer One")
                .telefone("123456789")
                .descricao("Desenvolvedor Java")
                .build();

        doNothing().when(freelancerService).atualizarDadosFreelancer(requestDto);

        ResponseEntity<?> response = freelancerController.atualizarFreelancer(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        StandardResponse standardResponse = (StandardResponse) response.getBody();
        assertEquals("Freelancer atualizado com sucesso!", standardResponse.getMessage());

        verify(freelancerService).atualizarDadosFreelancer(requestDto);
    }
}
