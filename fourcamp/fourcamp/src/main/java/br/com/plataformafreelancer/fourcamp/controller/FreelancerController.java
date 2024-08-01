package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.*;
import br.com.plataformafreelancer.fourcamp.model.Projeto;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.FreelancerService;
import br.com.plataformafreelancer.fourcamp.utils.LoggerUtils;
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

    @PostMapping("/v1/cadastrar-freelancer")
    public ResponseEntity<?> cadastrarFreelancer(@RequestBody RequestFreelancerDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarFreelancer", request);
        long startTime = System.currentTimeMillis();

        try {
            service.salvarDadosCadastrais(request);
            ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Freelancer Cadastrado com Sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "cadastrarFreelancer", startTime);
            return ok;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "cadastrarFreelancer", request, e);
            throw e;
        }
    }

    @PostMapping("/v1/enviar-proposta")
    public ResponseEntity<?> enviarProposta(@RequestBody RequestPropostaDto request) {
        LoggerUtils.logRequestStart(LOGGER, "enviarProposta", request);
        long startTime = System.currentTimeMillis();

        try {
            service.salvarProposta(request);
            ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Proposta enviada com sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "enviarProposta", startTime);
            return ok;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "enviarProposta", request, e);
            throw e;
        }
    }

    @PostMapping("v1/avaliar-empresa")
    public ResponseEntity<?> avaliarEmpresa(@RequestBody RequestAvaliacaoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "avaliarEmpresa", request);
        long startTime = System.currentTimeMillis();

        try {
            service.avaliarEmpresa(request);
            ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Avaliação enviada!").build());
            LoggerUtils.logElapsedTime(LOGGER, "avaliarEmpresa", startTime);
            return ok;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "avaliarEmpresa", request, e);
            throw e;
        }
    }

    @GetMapping("/v1/listar-empresas")
    public ResponseEntity<?> listaEmpresa() {
        long startTime = System.currentTimeMillis();
        List<ResponseEmpresaDto> lista = service.listarEmpresa();
        LoggerUtils.logElapsedTime(LOGGER, "listaEmpresa", startTime);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/v1/listar-projetos")
    public ResponseEntity<?> listaProjetos() {
        long startTime = System.currentTimeMillis();
        List<Projeto> lista = service.listarTodosProjetos();
        LoggerUtils.logElapsedTime(LOGGER, "listaProjetos", startTime);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/v1/exibir-detalhes-empresa/{id}")
    public ResponseEntity<?> exibirDetalhesEmpresa(@PathVariable("id") Integer id) {
        long startTime = System.currentTimeMillis();
        ResponseEmpresaCompletaDto empresa = service.obterDetalhesEmpresa(id);
        LoggerUtils.logElapsedTime(LOGGER, "exibirDetalhesEmpresa", startTime);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @GetMapping("/v1/buscar-projeto-compativel/{id}")
    public ResponseEntity<?> buscarProjetoCompativel(@PathVariable("id") Integer id) {
        long startTime = System.currentTimeMillis();
        List<ProjetoCompatibilidadeDto> empresa = service.listaProjetosCompativeis(id);
        LoggerUtils.logElapsedTime(LOGGER, "buscarProjetoCompativel", startTime);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @PutMapping("/v1/atualizar-freelancer")
    public ResponseEntity<?> atualizarFreelancer(@RequestBody RequestAtualizarFreelancerDto request) {
        LoggerUtils.logRequestStart(LOGGER, "atualizarFreelancer", request);
        long startTime = System.currentTimeMillis();

        try {
            service.atualizarDadosFreelancer(request);
            ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Freelancer Atualizado com Sucesso!").build());
            LoggerUtils.logElapsedTime(LOGGER, "atualizarFreelancer", startTime);
            return ok;
        } catch (Exception e) {
            LoggerUtils.logError(LOGGER, "atualizarFreelancer", request, e);
            throw e;
        }
    }
}
