package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.RequestAvaliacaoDto;
import br.com.plataformafreelancer.fourcamp.dtos.RequestFreelancerDto;
import br.com.plataformafreelancer.fourcamp.dtos.RequestPropostaDto;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.FreelancerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/freelancer")
public class FreelancerController {
    @Autowired
    FreelancerService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(FreelancerController.class);

    @PostMapping("/v1/cadastrar-freelancer")
    public ResponseEntity<?> cadastrarFreelancer(@RequestBody RequestFreelancerDto request) {
        LOGGER.info("Início do método cadastrarFreelancer com request: {}", request);
        long startTime = System.currentTimeMillis();

        try {
            service.salvarDadosCadastrais(request);
            ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Freelancer Cadastrado com Sucesso!").build());
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            LOGGER.info("Tempo decorrido no método cadastrarFreelancer: {} milissegundos", elapsedTime);
            return ok;
        } catch (Exception e) {
            LOGGER.error("Erro ao cadastrar freelancer: {}", request, e);
            throw e;
        }
    }

    @PostMapping("/v1/enviar-proposta")
    public ResponseEntity<?> enviarProposta(@RequestBody RequestPropostaDto request) {
        LOGGER.info("Início do método enviarProposta com request: {}", request);
        long startTime = System.currentTimeMillis();

        try {
            service.salvarProposta(request);
            ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Proposta enviada com sucesso!").build());
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            LOGGER.info("Tempo decorrido no método enviarProposta: {} milissegundos", elapsedTime);
            return ok;
        } catch (Exception e) {
            LOGGER.error("Erro ao enviar proposta: {}", request, e);
            throw e;
        }
    }

    @PostMapping("v1/avaliar-empresa")
    public ResponseEntity<?> avaliarEmpresa(@RequestBody RequestAvaliacaoDto request) {
        LOGGER.info("Início do método avaliarEmpresa com request: {}", request);
        long startTime = System.currentTimeMillis();

        try {
            service.avaliarEmpresa(request);
            ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Avaliação enviada!").build());
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            LOGGER.info("Tempo decorrido no método avaliarEmpresa: {} milissegundos", elapsedTime);
            return ok;
        } catch (Exception e) {
            LOGGER.error("Erro ao avaliar empresa: {}", request, e);
            throw e;
        }
    }
}
