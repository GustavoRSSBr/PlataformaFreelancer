package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.*;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.FreelancerService;
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
@RequestMapping("/freelancer")
public class FreelancerController {
    @Autowired
    FreelancerService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(FreelancerController.class);

    @Operation(summary = "Cadastrar um novo freelancer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/cadastrar-freelancer")
    public ResponseEntity<?> cadastrarFreelancer(@RequestBody RequestFreelancerDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarFreelancer", request);
        long startTime = System.currentTimeMillis();

        service.salvarDadosCadastrais(request);
        ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Freelancer cadastrado com sucesso!").build());
        LoggerUtils.logElapsedTime(LOGGER, "cadastrarFreelancer", startTime);
        return response;
    }

    @Operation(summary = "Enviar uma proposta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proposta enviada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/enviar-proposta")
    public ResponseEntity<?> enviarProposta(@RequestBody RequestPropostaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "enviarProposta", request);
        long startTime = System.currentTimeMillis();

        service.salvarProposta(request);
        ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Proposta enviada com sucesso!").build());
        LoggerUtils.logElapsedTime(LOGGER, "enviarProposta", startTime);
        return response;
    }

    @Operation(summary = "Avaliar uma empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação enviada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/v1/avaliar-empresa")
    public ResponseEntity<?> avaliarEmpresa(@RequestBody RequestAvaliacaoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarEmpresa", request);
        long startTime = System.currentTimeMillis();

        service.avaliarEmpresa(request);
        ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Avaliação enviada com sucesso!").build());
        LoggerUtils.logElapsedTime(LOGGER, "avaliarEmpresa", startTime);
        return response;
    }

    @Operation(summary = "Listar empresas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresas listadas com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/listar-empresas")
    public ResponseEntity<?> listaEmpresa() {
        long startTime = System.currentTimeMillis();
        List<ResponseEmpresaDto> lista = service.listarEmpresa();
        LoggerUtils.logElapsedTime(LOGGER, "listaEmpresa", startTime);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Listar projetos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projetos listados com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/listar-projetos")
    public ResponseEntity<?> listaProjetos() {
        long startTime = System.currentTimeMillis();
        List<Projeto> lista = service.listarTodosProjetos();
        LoggerUtils.logElapsedTime(LOGGER, "listaProjetos", startTime);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Exibir detalhes de uma empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes da empresa exibidos com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/exibir-detalhes-empresa/{id}")
    public ResponseEntity<?> exibirDetalhesEmpresa(@PathVariable("id") Integer id) {
        long startTime = System.currentTimeMillis();
        ResponseEmpresaCompletaDto empresa = service.obterDetalhesEmpresa(id);
        LoggerUtils.logElapsedTime(LOGGER, "exibirDetalhesEmpresa", startTime);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @Operation(summary = "Buscar projetos compatíveis com freelancer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projetos compatíveis listados com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/v1/buscar-projeto-compativel/{id}")
    public ResponseEntity<?> buscarProjetoCompativel(@PathVariable("id") Integer id) {
        long startTime = System.currentTimeMillis();
        List<ProjetoCompatibilidadeDto> empresa = service.listaProjetosCompativeis(id);
        LoggerUtils.logElapsedTime(LOGGER, "buscarProjetoCompativel", startTime);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @Operation(summary = "Atualizar dados de um freelancer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/v1/atualizar-freelancer")
    public ResponseEntity<?> atualizarFreelancer(@RequestBody RequestAtualizarFreelancerDto request) {
        LoggerUtils.logRequestStart(LOGGER, "atualizarFreelancer", request);
        long startTime = System.currentTimeMillis();

        service.atualizarDadosFreelancer(request);
        ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Freelancer atualizado com sucesso!").build());
        LoggerUtils.logElapsedTime(LOGGER, "atualizarFreelancer", startTime);
        return response;
    }
}
