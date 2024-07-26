package br.com.plataformafreelancer.fourcamp.controller;
import br.com.plataformafreelancer.fourcamp.dtos.RequestAnalisarPropostaDto;
import br.com.plataformafreelancer.fourcamp.dtos.RequestAvaliacaoDto;
import br.com.plataformafreelancer.fourcamp.dtos.RequestEmpresaDto;
import br.com.plataformafreelancer.fourcamp.dtos.RequestProjetoDto;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    EmpresaService service;

    @PostMapping("/v1/cadastrar-empresa")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody RequestEmpresaDto request) {
        LOGGER.info("Início do método cadastrarEmpresa com request: {}", request);
        long startTime = System.currentTimeMillis();

        try {
            service.salvarDadosCadastrais(request);
            ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Empresa Cadastrada com Sucesso!").build());
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            LOGGER.info("Tempo decorrido no método cadastrarEmpresa: {} milissegundos", elapsedTime);
            return ok;
        } catch (Exception e) {
            LOGGER.error("Erro ao cadastrar empresa: {}", request, e);
            throw e;
        }
    }

    @PostMapping("/v1/cadastrar-projeto")
    public ResponseEntity<?> cadastrarProjeto(@RequestBody RequestProjetoDto request) {
        LOGGER.info("Início do método cadastrarProjeto com request: {}", request);
        long startTime = System.currentTimeMillis();

        try {
            service.salvarDadosProjeto(request);
            ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Projeto cadastrado com Sucesso!").build());
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            LOGGER.info("Tempo decorrido no método cadastrarProjeto: {} milissegundos", elapsedTime);
            return ok;
        } catch (Exception e) {
            LOGGER.error("Erro ao cadastrar projeto: {}", request, e);
            throw e;
        }
    }

    @PutMapping("/v1/analisar-proposta")
    public ResponseEntity<?> analisarProposta(@RequestBody RequestAnalisarPropostaDto request) {
        LOGGER.info("Início do método analisarProposta com request: {}", request);
        long startTime = System.currentTimeMillis();

        try {
            service.analisarProposta(request);
            ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Proposta atualizada!").build());
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            LOGGER.info("Tempo decorrido no método analisarProposta: {} milissegundos", elapsedTime);
            return ok;
        } catch (Exception e) {
            LOGGER.error("Erro ao analisar proposta: {}", request, e);
            throw e;
        }
    }

    @PostMapping("/v1/avaliar-freelancer")
    public ResponseEntity<?> avaliarFreelancer(@RequestBody RequestAvaliacaoDto request) {
        LOGGER.info("Início do método avaliarFreelancer com request: {}", request);
        long startTime = System.currentTimeMillis();

        try {
            service.avaliarFreelancer(request);
            ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Avaliação enviada!").build());
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            LOGGER.info("Tempo decorrido no método avaliarFreelancer: {} milissegundos", elapsedTime);
            return ok;
        } catch (Exception e) {
            LOGGER.error("Erro ao avaliar freelancer: {}", request, e);
            throw e;
        }
    }
}
