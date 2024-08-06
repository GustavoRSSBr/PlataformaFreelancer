package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.*;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseFreelancerCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponsePropostaDto;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.EmpresaService;
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

public class EmpresaControllerTest {

    @Mock
    private EmpresaService empresaService;

    @InjectMocks
    private EmpresaController empresaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarEmpresa() {
        RequestEmpresaDto requestDto = new RequestEmpresaDto();
        doNothing().when(empresaService).salvarDadosCadastrais(requestDto);

        ResponseEntity<?> response = empresaController.cadastrarEmpresa(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        StandardResponse standardResponse = (StandardResponse) response.getBody();
        assertEquals("Empresa cadastrada com sucesso!", standardResponse.getMessage());

        verify(empresaService).salvarDadosCadastrais(requestDto);
    }

    @Test
    public void testCadastrarProjeto() {
        RequestProjetoDto requestDto = new RequestProjetoDto();
        doNothing().when(empresaService).salvarDadosProjeto(requestDto);

        ResponseEntity<?> response = empresaController.cadastrarProjeto(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        StandardResponse standardResponse = (StandardResponse) response.getBody();
        assertEquals("Projeto cadastrado com sucesso!", standardResponse.getMessage());

        verify(empresaService).salvarDadosProjeto(requestDto);
    }

    @Test
    public void testAnalisarProposta() {
        RequestAnalisarPropostaDto requestDto = new RequestAnalisarPropostaDto();
        doNothing().when(empresaService).analisarProposta(requestDto);

        ResponseEntity<?> response = empresaController.analisarProposta(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        StandardResponse standardResponse = (StandardResponse) response.getBody();
        assertEquals("Proposta atualizada com sucesso!", standardResponse.getMessage());

        verify(empresaService).analisarProposta(requestDto);
    }

    @Test
    public void testAvaliarFreelancer() {
        RequestAvaliacaoDto requestDto = new RequestAvaliacaoDto();
        doNothing().when(empresaService).avaliarFreelancer(requestDto);

        ResponseEntity<?> response = empresaController.avaliarFreelancer(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        StandardResponse standardResponse = (StandardResponse) response.getBody();
        assertEquals("Avaliação enviada com sucesso!", standardResponse.getMessage());

        verify(empresaService).avaliarFreelancer(requestDto);
    }

    @Test
    public void testListaFreelancer() {
        ResponseFreelancerDto freelancer1 = ResponseFreelancerDto.builder()
                .idFreelancer(1)
                .email("freelancer1@example.com")
                .nome("Freelancer One")
                .dataNascimento("1990-01-01")
                .telefone("123456789")
                .cidade("São Paulo")
                .estado("SP")
                .descricao("Desenvolvedor Java")
                .disponibilidade("Full-time")
                .dataCriacao("2023-08-01")
                .habilidades(Arrays.asList("Java", "Spring", "REST"))
                .build();

        ResponseFreelancerDto freelancer2 = ResponseFreelancerDto.builder()
                .idFreelancer(2)
                .email("freelancer2@example.com")
                .nome("Freelancer Two")
                .dataNascimento("1985-05-15")
                .telefone("987654321")
                .cidade("Rio de Janeiro")
                .estado("RJ")
                .descricao("Designer Gráfico")
                .disponibilidade("Part-time")
                .dataCriacao("2023-07-15")
                .habilidades(Arrays.asList("Photoshop", "Illustrator", "UI/UX"))
                .build();

        List<ResponseFreelancerDto> freelancersMock = Arrays.asList(freelancer1, freelancer2);

        when(empresaService.listarFreelancer()).thenReturn(freelancersMock);

        ResponseEntity<?> response = empresaController.listaFreelancer();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ResponseFreelancerDto> responseBody = (List<ResponseFreelancerDto>) response.getBody();
        assertEquals(2, responseBody.size());
        assertEquals(freelancer1, responseBody.get(0));
        assertEquals(freelancer2, responseBody.get(1));

        verify(empresaService).listarFreelancer();
    }

    @Test
    public void testExibirDetalheFreelancer() {
        Integer freelancerId = 1;
        ResponseFreelancerCompletaDto freelancerMock = ResponseFreelancerCompletaDto.builder()
                .idFreelancer(freelancerId)
                .email("freelancer1@example.com")
                .nome("Freelancer One")
                .dataNascimento("1990-01-01")
                .telefone("123456789")
                .logradouro("Rua Exemplo")
                .numero("123")
                .complemento("Apto 45")
                .bairro("Centro")
                .cidade("São Paulo")
                .cep("01000-000")
                .estado("SP")
                .pais("Brasil")
                .descricao("Desenvolvedor Java")
                .disponibilidade("Full-time")
                .dataCriacao("2023-08-01")
                .statusFreelancer("ATIVO")
                .notaMedia(4.5)
                .habilidades(Arrays.asList("Java", "Spring", "REST"))
                .build();

        when(empresaService.obterDetalhesFreelancer(freelancerId)).thenReturn(freelancerMock);

        ResponseEntity<?> response = empresaController.exibirDetalheFreelancer(freelancerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponseFreelancerCompletaDto responseBody = (ResponseFreelancerCompletaDto) response.getBody();
        assertEquals(freelancerMock, responseBody);

        verify(empresaService).obterDetalhesFreelancer(freelancerId);
    }

    @Test
    public void testBuscarPropostaPorProjeto() {
        Integer projetoId = 1;

        ResponsePropostaDto proposta1 = ResponsePropostaDto.builder()
                .propostaId(1)
                .freelancerNome("Freelancer One")
                .freelancerTelefone("123456789")
                .freelancerEmail("freelancer1@example.com")
                .projetoId(projetoId)
                .valor("5000.00")
                .dataCriacao("2023-08-01")
                .statusProposta(StatusProposta.PENDENTE)
                .observacao("Proposta inicial")
                .build();

        ResponsePropostaDto proposta2 = ResponsePropostaDto.builder()
                .propostaId(2)
                .freelancerNome("Freelancer Two")
                .freelancerTelefone("987654321")
                .freelancerEmail("freelancer2@example.com")
                .projetoId(projetoId)
                .valor("8000.00")
                .dataCriacao("2023-08-02")
                .statusProposta(StatusProposta.ACEITA)
                .observacao("Proposta revisada")
                .build();

        List<ResponsePropostaDto> propostasMock = Arrays.asList(proposta1, proposta2);

        when(empresaService.listarPropostasPorProjeto(projetoId)).thenReturn(propostasMock);

        ResponseEntity<?> response = empresaController.buscarPropostaPorProjeto(projetoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ResponsePropostaDto> responseBody = (List<ResponsePropostaDto>) response.getBody();
        assertEquals(2, responseBody.size());
        assertEquals(proposta1, responseBody.get(0));
        assertEquals(proposta2, responseBody.get(1));

        verify(empresaService).listarPropostasPorProjeto(projetoId);
    }

    @Test
    public void testAtualizarEmpresa() {
        RequestAtualizarEmpresaDto requestDto = RequestAtualizarEmpresaDto.builder()
                .idEmpresa(1)
                .nome("Empresa Exemplo")
                .telefone("123456789")
                .ramoAtuacao("Tecnologia")
                .site("https://www.empresaexemplo.com")
                .build();

        doNothing().when(empresaService).atualizarDadosEmpresa(requestDto);

        ResponseEntity<?> response = empresaController.atualizarEmpresa(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        StandardResponse standardResponse = (StandardResponse) response.getBody();
        assertEquals("Empresa atualizada com sucesso!", standardResponse.getMessage());

        verify(empresaService).atualizarDadosEmpresa(requestDto);
    }

    @Test
    public void testAtualizarProjeto() {
        RequestAtualizarProjetoDto requestDto = RequestAtualizarProjetoDto.builder()
                .idProjeto(1)
                .titulo("Novo Sistema")
                .descricao("Desenvolvimento de um novo sistema de gestão")
                .orcamento("10000.00")
                .prazo("2024-01-01")
                .habilidades(Arrays.asList("Java", "Spring", "MySQL"))
                .build();

        doNothing().when(empresaService).atualizarDadosProjeto(requestDto);

        ResponseEntity<?> response = empresaController.atualizarProjeto(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        StandardResponse standardResponse = (StandardResponse) response.getBody();
        assertEquals("Projeto atualizado com sucesso!", standardResponse.getMessage());

        verify(empresaService).atualizarDadosProjeto(requestDto);
    }

    @Test
    public void testExcluirProjeto() {
        Integer idProjeto = 1;

        doNothing().when(empresaService).excluirProjetoSeNaoAssociado(idProjeto);

        ResponseEntity<?> response = empresaController.excluirProjeto(idProjeto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        StandardResponse standardResponse = (StandardResponse) response.getBody();
        assertEquals("Projeto excluído com sucesso!", standardResponse.getMessage());

        verify(empresaService).excluirProjetoSeNaoAssociado(idProjeto);
    }
}
