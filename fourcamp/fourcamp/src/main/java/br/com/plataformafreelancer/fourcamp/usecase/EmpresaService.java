package br.com.plataformafreelancer.fourcamp.usecase;
import br.com.plataformafreelancer.fourcamp.dao.impl.EmpresaJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.EnderecoDto;
import br.com.plataformafreelancer.fourcamp.dtos.RequestEmpresaDto;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.model.Empresa;
import br.com.plataformafreelancer.fourcamp.model.Endereco;
import br.com.plataformafreelancer.fourcamp.model.Usuario;
import br.com.plataformafreelancer.fourcamp.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    @Autowired
    EmailService emailService;

    @Autowired
    SenhaService senhaService;

    @Autowired
    CepService cepService;

    @Autowired
    CnpjService cnpjService;

    @Autowired
    TelefoneService telefoneService;

    @Autowired
    EmpresaJdbcTemplateDaoImpl empresaJdbcTemplateDao;

    public void salvarDadosCadastrais(RequestEmpresaDto request){

        emailService.validarEmail(request.getEmail());
        senhaService.validarSenha(request.getSenha());
        EnderecoDto enderecoDto = cepService.buscaEnderecoPor(request.getCep());
        cnpjService.validarCnpj(request.getCnpj());
        telefoneService.validarNumeroTelefone(request.getTelefone());

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .senha(request.getSenha())
                .tipoUsuario(TipoUsuario.EMPRESA)
                .build();

        Endereco endereco = Endereco.builder()
                .logradouro(enderecoDto.getLogradouro())
                .numero(request.getNumero())
                .complemento(request.getComplemento())
                .bairro(enderecoDto.getBairro())
                .cidade(enderecoDto.getLocalidade())
                .cep(enderecoDto.getCep())
                .estado(enderecoDto.getUf())
                .pais(request.getPais())
                .build();

        Empresa empresa = Empresa.builder()
                .usuario(usuario)
                .cnpj(request.getCnpj())
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .endereco(endereco)
                .nomeEmpresa(request.getNomeEmpresa())
                .ramoAtuacao(request.getRamoAtuacao())
                .site(request.getSite())
                .build();

        empresaJdbcTemplateDao.salvarDadosCadastrais(empresa);
    }
}
