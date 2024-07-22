package br.com.plataformafreelancer.fourcamp.controller;

import br.com.plataformafreelancer.fourcamp.dtos.RequestFreelancerDto;
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
        System.out.println(request);
        long startTime = System.currentTimeMillis();
        service.salvarDadosCadastrais(request);
        ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Freelancer Cadastrado com Sucesso!").build());
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");
        return ok;
    }
}
