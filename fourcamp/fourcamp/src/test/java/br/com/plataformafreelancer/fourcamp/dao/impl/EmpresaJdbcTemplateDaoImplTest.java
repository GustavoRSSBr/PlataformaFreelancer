package br.com.plataformafreelancer.fourcamp.dao.impl;

import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.FreelancerCompletaDtoRowMapper;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.FreelancerDtoRowMapper;
import br.com.plataformafreelancer.fourcamp.dao.impl.mapper.PropostaRowMapper;
import br.com.plataformafreelancer.fourcamp.dtos.*;
import br.com.plataformafreelancer.fourcamp.model.*;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProjeto;
import br.com.plataformafreelancer.fourcamp.enuns.StatusProposta;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class EmpresaJdbcTemplateDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private EmpresaJdbcTemplateDaoImpl empresaJdbcTemplateDaoImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarDadosCadastrais() {
        Usuario usuario = new Usuario();
        usuario.setEmail("email@example.com");
        usuario.setSenha("senha123");
        usuario.setTipoUsuario(TipoUsuario.EMPRESA);

        Empresa empresa = new Empresa();
        empresa.setUsuario(usuario);
        empresa.setCnpj("00.000.000/0000-00");
        empresa.setNome("Empresa Teste");
        empresa.setTelefone("123456789");
        empresa.setEndereco(new Endereco(1 , "Rua Tal", "1000", "lado do cicrano", "Bairro", "São Paulo", "12345-678", "SP", "Brasil"));
        empresa.setNomeEmpresa("Nome Empresa");
        empresa.setRamoAtuacao("Tecnologia");
        empresa.setSite("www.empresa.com");

        when(jdbcTemplate.update(eq("CALL cadastrar_empresa(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"), any(Object[].class)))
                .thenReturn(1);

        empresaJdbcTemplateDaoImpl.salvarDadosCadastrais(empresa);

        verify(jdbcTemplate, times(1)).update(eq("CALL cadastrar_empresa(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"), any(Object[].class));
    }

    @Test
    public void testSalvarDadosProjeto() {
        Projeto projeto = new Projeto();
        projeto.setTitulo("Projeto Teste");
        projeto.setDescricao("Descrição do Projeto Teste");
        projeto.setOrcamento("10000.0");
        projeto.setPrazo("2024-12-31");
        projeto.setStatusProjeto(StatusProjeto.ATIVO);
        projeto.setHabilidades(Arrays.asList("Java", "Spring"));
        projeto.setEmpresaId(1);

        when(jdbcTemplate.update(eq("CALL cadastrarProjeto(?, ?, ?, ?, ?, ?, ?, ?)"), any(Object[].class)))
                .thenReturn(1);

        empresaJdbcTemplateDaoImpl.salvarDadosProjeto(projeto);

        verify(jdbcTemplate, times(1)).update(eq("CALL cadastrarProjeto(?, ?, ?, ?, ?, ?, ?, ?)"), any(Object[].class));
    }

    @Test
    public void testAnalisarProposta() {
        RequestAnalisarPropostaDto request = new RequestAnalisarPropostaDto();
        request.setIdProposta(1);
        request.setStatusProposta(StatusProposta.ACEITA);

        when(jdbcTemplate.update(eq("CALL AtualizaStatusProposta(?, ?)"), any(Object[].class)))
                .thenReturn(1);

        empresaJdbcTemplateDaoImpl.analisarProposta(request);

        verify(jdbcTemplate, times(1)).update(eq("CALL AtualizaStatusProposta(?, ?)"), any(Object[].class));
    }

    @Test
    public void testAvaliarFreelancer() {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setEmpresaId(1);
        avaliacao.setFreelancerId(2);
        avaliacao.setProjetoId(3);
        avaliacao.setAvaliado(TipoUsuario.FREELANCER);
        avaliacao.setNota(5);
        avaliacao.setComentario("Excelente trabalho");
        avaliacao.setDataAvaliacao("2024-01-01");

        when(jdbcTemplate.update(eq("CALL enviar_avaliacao(?, ?, ?, ?, ?, ?, ?)"), any(Object[].class)))
                .thenReturn(1);

        empresaJdbcTemplateDaoImpl.avaliarFreelancer(avaliacao);

        verify(jdbcTemplate, times(1)).update(eq("CALL enviar_avaliacao(?, ?, ?, ?, ?, ?, ?)"), any(Object[].class));
    }

    @Test
    public void testListarFreelancer() {
        List<ResponseFreelancerDto> expectedList = Arrays.asList(new ResponseFreelancerDto(), new ResponseFreelancerDto());

        when(jdbcTemplate.query(anyString(), any(FreelancerDtoRowMapper.class))).thenReturn(expectedList);

        List<ResponseFreelancerDto> actualList = empresaJdbcTemplateDaoImpl.listarFreelacer();

        assertEquals(expectedList, actualList);
        verify(jdbcTemplate, times(1)).query(anyString(), any(FreelancerDtoRowMapper.class));
    }

    @Test
    public void testObterDetalhesFreelancer() {
        ResponseFreelancerCompletaDto expectedFreelancer = new ResponseFreelancerCompletaDto();

        when(jdbcTemplate.queryForObject(eq("SELECT * FROM obter_detalhes_freelancer(?)"), any(Object[].class), any(FreelancerCompletaDtoRowMapper.class)))
                .thenReturn(expectedFreelancer);

        ResponseFreelancerCompletaDto actualFreelancer = empresaJdbcTemplateDaoImpl.obterDetalhesFreelancer(1);

        assertEquals(expectedFreelancer, actualFreelancer);
        verify(jdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM obter_detalhes_freelancer(?)"), any(Object[].class), any(FreelancerCompletaDtoRowMapper.class));
    }

    @Test
    public void testListarPropostasPorProjeto() {
        List<ResponsePropostaDto> expectedList = Arrays.asList(new ResponsePropostaDto(), new ResponsePropostaDto());

        when(jdbcTemplate.query(eq("SELECT * FROM listar_propostas_por_projeto(?)"), any(Object[].class), any(PropostaRowMapper.class)))
                .thenReturn(expectedList);

        List<ResponsePropostaDto> actualList = empresaJdbcTemplateDaoImpl.listarPropostasPorProjeto(1);

        assertEquals(expectedList, actualList);
        verify(jdbcTemplate, times(1)).query(eq("SELECT * FROM listar_propostas_por_projeto(?)"), any(Object[].class), any(PropostaRowMapper.class));
    }

    @Test
    public void testAtualizarProjeto() {
        RequestAtualizarProjetoDto request = new RequestAtualizarProjetoDto();
        request.setIdProjeto(1);
        request.setTitulo("Projeto Atualizado");
        request.setDescricao("Descrição atualizada");
        request.setOrcamento("15000.0");
        request.setPrazo("2024-12-31");
        request.setHabilidades(Arrays.asList("Java", "Spring Boot"));

        when(jdbcTemplate.update(eq("CALL atualizar_projeto(?, ?, ?, ?, ?, ?)"), any(Object[].class)))
                .thenReturn(1);

        empresaJdbcTemplateDaoImpl.atualizarProjeto(request);

        verify(jdbcTemplate, times(1)).update(eq("CALL atualizar_projeto(?, ?, ?, ?, ?, ?)"), any(Object[].class));
    }
}
