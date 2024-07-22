package br.com.plataformafreelancer.fourcamp.controller;
import br.com.plataformafreelancer.fourcamp.dtos.RequestEmpresaDto;
import br.com.plataformafreelancer.fourcamp.model.StandardResponse;
import br.com.plataformafreelancer.fourcamp.usecase.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaController.class);
    @Autowired
    EmpresaService service;
    @PostMapping("/v1/cadastrar-empresa")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody RequestEmpresaDto request){
        long startTime = System.currentTimeMillis();
        service.salvarDadosCadastrais(request);
        ResponseEntity<StandardResponse> ok = ResponseEntity.ok(StandardResponse.builder().message("Empresa Cadastrada com Sucesso!").build());
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");
        return ok;
    }
}
