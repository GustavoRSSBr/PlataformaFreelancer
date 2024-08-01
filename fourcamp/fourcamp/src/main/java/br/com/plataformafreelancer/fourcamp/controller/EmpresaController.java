package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.*;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.EmpresaService;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
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

    @PostMapping("/v1/cadastrar-empresa")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody RequestEmpresaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarEmpresa", request);
        long startTime = System.currentTimeMillis();

        try {
            service.salvarDadosCadastrais(request);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Empresa Cadastrada com Sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "cadastrarEmpresa", startTime);
            return response;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "cadastrarEmpresa", request, e);
            throw e;
        }
    }

    @PostMapping("/v1/cadastrar-projeto")
    public ResponseEntity<?> cadastrarProjeto(@RequestBody RequestProjetoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarProjeto", request);
        long startTime = System.currentTimeMillis();

        try {
            service.salvarDadosProjeto(request);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Projeto cadastrado com Sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "cadastrarProjeto", startTime);
            return response;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "cadastrarProjeto", request, e);
            throw e;
        }
    }

    @PutMapping("/v1/analisar-proposta")
    public ResponseEntity<?> analisarProposta(@RequestBody RequestAnalisarPropostaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "analisarProposta", request);
        long startTime = System.currentTimeMillis();

        try {
            service.analisarProposta(request);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Proposta atualizada!").build());
            LoggerUtils.logElapsedTime(LOGGER, "analisarProposta", startTime);
            return response;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "analisarProposta", request, e);
            throw e;
        }
    }

    @PostMapping("/v1/avaliar-freelancer")
    public ResponseEntity<?> avaliarFreelancer(@RequestBody RequestAvaliacaoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarFreelancer", request);
        long startTime = System.currentTimeMillis();

        try {
            service.avaliarFreelancer(request);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Avaliação enviada!").build());
            LoggerUtils.logElapsedTime(LOGGER, "avaliarFreelancer", startTime);
            return response;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "avaliarFreelancer", request, e);
            throw e;
        }
    }

    @GetMapping("/v1/listar-freelancers")
    public ResponseEntity<?> listaFreelancer() {
        long startTime = System.currentTimeMillis();
        List<ResponseFreelancerDto> lista = service.listarFreelancer();
        LoggerUtils.logElapsedTime(LOGGER, "listarFreelancer", startTime);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/v1/exibir-detalhes-freelancer/{id}")
    public ResponseEntity<?> exibirDetalheFreelancer(@PathVariable("id") Integer id) {
        long startTime = System.currentTimeMillis();
        ResponseFreelancerCompletaDto freelancer = service.obterDetalhesFreelancer(id);
        LoggerUtils.logElapsedTime(LOGGER, "exibirDetalheFreelancer", startTime);
        return new ResponseEntity<>(freelancer, HttpStatus.OK);
    }

    @GetMapping("/v1/buscar-propostas-por-projeto/{id}")
    public ResponseEntity<?> buscarPropostaPorProjeto(@PathVariable("id") Integer id) {
        long startTime = System.currentTimeMillis();
        List<ResponsePropostaDto> propostas = service.listarPropostasPorProjeto(id);
        LoggerUtils.logElapsedTime(LOGGER, "buscarPropostaPorProjeto", startTime);
        return new ResponseEntity<>(propostas, HttpStatus.OK);
    }

    @PutMapping("/v1/atualizar-empresa")
    public ResponseEntity<?> atualizarEmpresa(@RequestBody RequestAtualizarEmpresaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "atualizarEmpresa", request);
        long startTime = System.currentTimeMillis();

        try {
            service.atualizarDadosEmpresa(request);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Empresa Atualizada com Sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "atualizarEmpresa", startTime);
            return response;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "atualizarEmpresa", request, e);
            throw e;
        }
    }

    @PutMapping("/v1/atualizar-projeto")
    public ResponseEntity<?> atualizarProjeto(@RequestBody RequestAtualizarProjetoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "atualizarProjeto", request);
        long startTime = System.currentTimeMillis();

        try {
            service.atualizarDadosProjeto(request);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(StandardResponse.builder().message("Projeto Atualizado com Sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "atualizarProjeto", startTime);
            return response;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "atualizarProjeto", request, e);
            throw e;
        }
    }

    @DeleteMapping("/v1/excluir-projeto/{id}")
    public ResponseEntity<?> excluirProjeto(@PathVariable("id") Integer idProjeto) {
        service.excluirProjetoSeNaoAssociado(idProjeto);
        return ResponseEntity.ok(StandardResponse.builder().message("Projeto excluido com sucesso!").build());
    }
}
