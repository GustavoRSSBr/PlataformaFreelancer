package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.*;
import br.com.plataformafreelancer.fourcamp.dtos.requestDtos.RequestAtualizarFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaCompletaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseEmpresaDto;
import br.com.plataformafreelancer.fourcamp.dtos.responseDtos.ResponseProjetoCompatibilidadeDto;
import br.com.plataformafreelancer.fourcamp.model.*;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import br.com.plataformafreelancer.fourcamp.enuns.StatusFreelancer;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class FreelancerJdbcTemplateDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private FreelancerJdbcTemplateDaoImpl freelancerJdbcTemplateDaoImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarDadosCadastrais() {
        Usuario usuario = new Usuario();
        usuario.setEmail("email@freelancer.com");
        usuario.setSenha("senha123");
        usuario.setTipoUsuario(TipoUsuario.FREELANCER);

        Freelancer freelancer = new Freelancer();
        freelancer.setUsuario(usuario);
        freelancer.setEndereco(new Endereco(1,"Rua Freelancer", "123", "Apto 1", "Bairro", "Cidade", "12345-678", "Estado", "País"));
        freelancer.setNome("Freelancer Teste");
        freelancer.setCpf("000.000.000-00");
        freelancer.setDataNascimento(LocalDate.parse("1990-01-01"));
        freelancer.setTelefone("123456789");
        freelancer.setDescricao("Desenvolvedor Java");
        freelancer.setDisponibilidade("Full-Time");
        freelancer.setDataCriacao("2023-01-01");
        freelancer.setStatusFreelancer(StatusFreelancer.ATIVO);
        freelancer.setHabilidades(Arrays.asList("Java", "Spring"));

        when(jdbcTemplate.update(eq("CALL cadastrar_freelancer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"), any(Object[].class)))
                .thenReturn(1);

        freelancerJdbcTemplateDaoImpl.salvarDadosCadastrais(freelancer);

        verify(jdbcTemplate, times(1)).update(eq("CALL cadastrar_freelancer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"), any(Object[].class));
    }

    @Test
    public void testSalvarProposta() {
        Proposta proposta = new Proposta();
        proposta.setFreelancerId(1);
        proposta.setProjetoId(2);
        proposta.setValor("5000.0");
        proposta.setDataCriacao("2024-01-01");
        proposta.setStatusProposta(StatusProposta.ACEITA);
        proposta.setObservacao("Proposta enviada com sucesso");

        when(jdbcTemplate.update(eq("CALL criar_proposta(?, ?, ?, ?, ?, ?)"), any(Object[].class)))
                .thenReturn(1);

        freelancerJdbcTemplateDaoImpl.salvarProposta(proposta);

        verify(jdbcTemplate, times(1)).update(eq("CALL criar_proposta(?, ?, ?, ?, ?, ?)"), any(Object[].class));
    }

    @Test
    public void testAvaliarEmpresa() {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setEmpresaId(1);
        avaliacao.setFreelancerId(2);
        avaliacao.setProjetoId(3);
        avaliacao.setAvaliado(TipoUsuario.EMPRESA);
        avaliacao.setNota(5);
        avaliacao.setComentario("Empresa excelente");
        avaliacao.setDataAvaliacao("2024-01-01");

        when(jdbcTemplate.update(eq("CALL enviar_avaliacao(?, ?, ?, ?, ?, ?, ?)"), any(Object[].class)))
                .thenReturn(1);

        freelancerJdbcTemplateDaoImpl.avaliarEmpresa(avaliacao);

        verify(jdbcTemplate, times(1)).update(eq("CALL enviar_avaliacao(?, ?, ?, ?, ?, ?, ?)"), any(Object[].class));
    }

    @Test
    public void testListarEmpresas() {
        List<ResponseEmpresaDto> expectedList = Arrays.asList(new ResponseEmpresaDto(), new ResponseEmpresaDto());

        when(jdbcTemplate.query(anyString(), any(EmpresaDtoRowMapper.class))).thenReturn(expectedList);

        List<ResponseEmpresaDto> actualList = freelancerJdbcTemplateDaoImpl.listarEmpresas();

        assertEquals(expectedList, actualList);
        verify(jdbcTemplate, times(1)).query(anyString(), any(EmpresaDtoRowMapper.class));
    }

    @Test
    public void testListarTodosProjetos() {
        List<Projeto> expectedList = Arrays.asList(new Projeto(), new Projeto());

        when(jdbcTemplate.query(anyString(), any(ProjetoDtoRowMapper.class))).thenReturn(expectedList);

        List<Projeto> actualList = freelancerJdbcTemplateDaoImpl.listarTodosProjetos();

        assertEquals(expectedList, actualList);
        verify(jdbcTemplate, times(1)).query(anyString(), any(ProjetoDtoRowMapper.class));
    }

    @Test
    public void testObterDetalhesEmpresa() {
        ResponseEmpresaCompletaDto expectedEmpresa = new ResponseEmpresaCompletaDto();

        when(jdbcTemplate.queryForObject(eq("SELECT * FROM obter_detalhes_empresa(?)"), any(Object[].class), any(EmpresaCompletaDtoRowMapper.class)))
                .thenReturn(expectedEmpresa);

        ResponseEmpresaCompletaDto actualEmpresa = freelancerJdbcTemplateDaoImpl.obterDetalhesEmpresa(1);

        assertEquals(expectedEmpresa, actualEmpresa);
        verify(jdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM obter_detalhes_empresa(?)"), any(Object[].class), any(EmpresaCompletaDtoRowMapper.class));
    }

    @Test
    public void testBuscarProjetosCompativeis() {
        List<ResponseProjetoCompatibilidadeDto> expectedList = Arrays.asList(new ResponseProjetoCompatibilidadeDto(), new ResponseProjetoCompatibilidadeDto());

        when(jdbcTemplate.query(eq("SELECT * FROM buscar_projetos_compatíveis(?)"), any(Object[].class), any(ProjetoCompatibilidadeDtoRowMapper.class)))
                .thenReturn(expectedList);

        List<ResponseProjetoCompatibilidadeDto> actualList = freelancerJdbcTemplateDaoImpl.buscarProjetosCompativeis(1);

        assertEquals(expectedList, actualList);
        verify(jdbcTemplate, times(1)).query(eq("SELECT * FROM buscar_projetos_compatíveis(?)"), any(Object[].class), any(ProjetoCompatibilidadeDtoRowMapper.class));
    }

    @Test
    public void testAtualizarDadosFreelancer() {
        RequestAtualizarFreelancerDto request = new RequestAtualizarFreelancerDto();
        request.setIdFreelancer(1);
        request.setTelefone("987654321");
        request.setLogradouro("Nova Rua");
        request.setNumero("321");
        request.setComplemento("Apto 2");
        request.setBairro("Novo Bairro");
        request.setCidade("Nova Cidade");
        request.setCep("87654-321");
        request.setEstado("Novo Estado");
        request.setPais("Novo País");
        request.setDescricao("Freelancer atualizado");
        request.setDisponibilidade("Part-Time");
        request.setStatus(String.valueOf(StatusFreelancer.INATIVO));
        request.setHabilidades(Arrays.asList("Python", "Django"));

        when(jdbcTemplate.update(eq("CALL atualizar_freelancer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"), any(Object[].class)))
                .thenReturn(1);

        freelancerJdbcTemplateDaoImpl.atualizarDadosFreelancer(request);

        verify(jdbcTemplate, times(1)).update(eq("CALL atualizar_freelancer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"), any(Object[].class));
    }
}
