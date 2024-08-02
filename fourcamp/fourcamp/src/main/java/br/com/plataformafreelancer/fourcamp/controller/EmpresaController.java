package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.*;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.EmpresaService;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    EmpresaService service;

    @Operation(summary = "Cadastrar uma nova empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa cadastrada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/cadastrar-empresa")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody RequestEmpresaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarEmpresa", request);
        long startTime = System.currentTimeMillis();

        try {
            service.salvarDadosCadastrais(request);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Empresa cadastrada com sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "cadastrarEmpresa", startTime);
            return response;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "cadastrarEmpresa", request, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StandardResponse.builder().message("Erro interno no servidor").build());
        }
    }

    @Operation(summary = "Cadastrar um novo projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/cadastrar-projeto")
    public ResponseEntity<?> cadastrarProjeto(@RequestBody RequestProjetoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarProjeto", request);
        long startTime = System.currentTimeMillis();

        try {
            service.salvarDadosProjeto(request);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Projeto cadastrado com sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "cadastrarProjeto", startTime);
            return response;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "cadastrarProjeto", request, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StandardResponse.builder().message("Erro interno no servidor").build());
        }
    }

    @Operation(summary = "Analisar uma proposta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proposta atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/v1/analisar-proposta")
    public ResponseEntity<?> analisarProposta(@RequestBody RequestAnalisarPropostaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "analisarProposta", request);
        long startTime = System.currentTimeMillis();

        try {
            service.analisarProposta(request);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Proposta atualizada com sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "analisarProposta", startTime);
            return response;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "analisarProposta", request, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StandardResponse.builder().message("Erro interno no servidor").build());
        }
    }

    @Operation(summary = "Avaliar um freelancer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação enviada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/avaliar-freelancer")
    public ResponseEntity<?> avaliarFreelancer(@RequestBody RequestAvaliacaoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarFreelancer", request);
        long startTime = System.currentTimeMillis();

        try {
            service.avaliarFreelancer(request);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Avaliação enviada com sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "avaliarFreelancer", startTime);
            return response;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "avaliarFreelancer", request, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StandardResponse.builder().message("Erro interno no servidor").build());
        }
    }

    @Operation(summary = "Listar freelancers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancers listados com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/listar-freelancers")
    public ResponseEntity<?> listaFreelancer() {
        long startTime = System.currentTimeMillis();
        List<ResponseFreelancerDto> lista = service.listarFreelancer();
        LoggerUtils.logElapsedTime(LOGGER, "listarFreelancer", startTime);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Exibir detalhes de um freelancer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes do freelancer exibidos com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/exibir-detalhes-freelancer/{id}")
    public ResponseEntity<?> exibirDetalheFreelancer(@PathVariable("id") Integer id) {
        long startTime = System.currentTimeMillis();
        ResponseFreelancerCompletaDto freelancer = service.obterDetalhesFreelancer(id);
        LoggerUtils.logElapsedTime(LOGGER, "exibirDetalheFreelancer", startTime);
        return new ResponseEntity<>(freelancer, HttpStatus.OK);
    }

    @Operation(summary = "Buscar propostas por projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propostas listadas com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/buscar-propostas-por-projeto/{id}")
    public ResponseEntity<?> buscarPropostaPorProjeto(@PathVariable("id") Integer id) {
        long startTime = System.currentTimeMillis();
        List<ResponsePropostaDto> propostas = service.listarPropostasPorProjeto(id);
        LoggerUtils.logElapsedTime(LOGGER, "buscarPropostaPorProjeto", startTime);
        return new ResponseEntity<>(propostas, HttpStatus.OK);
    }

    @Operation(summary = "Atualizar dados da empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/v1/atualizar-empresa")
    public ResponseEntity<?> atualizarEmpresa(@RequestBody RequestAtualizarEmpresaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "atualizarEmpresa", request);
        long startTime = System.currentTimeMillis();

        try {
            service.atualizarDadosEmpresa(request);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Empresa atualizada com sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "atualizarEmpresa", startTime);
            return response;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "atualizarEmpresa", request, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StandardResponse.builder().message("Erro interno no servidor").build());
        }
    }

    @Operation(summary = "Atualizar dados de um projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/v1/atualizar-projeto")
    public ResponseEntity<?> atualizarProjeto(@RequestBody RequestAtualizarProjetoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "atualizarProjeto", request);
        long startTime = System.currentTimeMillis();

        try {
            service.atualizarDadosProjeto(request);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Projeto atualizado com sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "atualizarProjeto", startTime);
            return response;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "atualizarProjeto", request, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StandardResponse.builder().message("Erro interno no servidor").build());
        }
    }

    @Operation(summary = "Excluir um projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto excluído com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/v1/excluir-projeto/{id}")
    public ResponseEntity<?> excluirProjeto(@PathVariable("id") Integer idProjeto) {
        service.excluirProjetoSeNaoAssociado(idProjeto);
        return ResponseEntity.ok(StandardResponse.builder().message("Projeto excluído com sucesso!").build());
    }
}
