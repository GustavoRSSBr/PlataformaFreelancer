package br.com.plataformafreelancer.fourcamp.utils;

import br.com.plataformafreelancer.fourcamp.dtos.EnderecoDto;
import br.com.plataformafreelancer.fourcamp.enuns.ErrorCode;
import br.com.plataformafreelancer.fourcamp.exceptions.CepException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {
    private static final String CEP_API_URL = "https://viacep.com.br/ws/%s/json/";

    public EnderecoDto buscaEnderecoPor(String cep) {
        if (!isValidCep(cep)) {
            throw new CepException(ErrorCode.CEP_INVALIDO.getCustomMessage() + cep);
        }

        RestTemplate restTemplate = new RestTemplate();
        EnderecoDto enderecoDto = restTemplate.getForObject(String.format(CEP_API_URL, cep), EnderecoDto.class);

        if (enderecoDto == null || enderecoDto.getCep() == null) {
            throw new CepException(ErrorCode.CEP_NAO_ENCONTRADO.getCustomMessage() + cep);
        }

        return enderecoDto;
    }

    private boolean isValidCep(String cep) {
        return cep != null && cep.matches("\\d{8}");
    }
}
