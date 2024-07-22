package br.com.plataformafreelancer.fourcamp.usecase;
import br.com.plataformafreelancer.fourcamp.dao.impl.FreelancerJdbcTemplateDaoImpl;
import br.com.plataformafreelancer.fourcamp.dtos.EnderecoDto;
import br.com.plataformafreelancer.fourcamp.dtos.RequestFreelancerDto;
import br.com.plataformafreelancer.fourcamp.enuns.Status;
import br.com.plataformafreelancer.fourcamp.enuns.TipoUsuario;
import br.com.plataformafreelancer.fourcamp.model.Endereco;
import br.com.plataformafreelancer.fourcamp.model.Freelancer;
import br.com.plataformafreelancer.fourcamp.model.Usuario;
import br.com.plataformafreelancer.fourcamp.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FreelancerService {
    @Autowired
    private CepService cepService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SenhaService senhaService;

    @Autowired
    private NomeService nomeService;

    @Autowired
    private DataService dataService;

    @Autowired
    private TelefoneService telefoneService;

    @Autowired
    private CpfService cpfService;

    @Autowired
    private FreelancerJdbcTemplateDaoImpl freelancerJdbcTemplateDaoImpl;


    public void salvarDadosCadastrais(RequestFreelancerDto request){
        emailService.validarEmail(request.getEmail());
        senhaService.validarSenha(request.getSenha());
        nomeService.validarNome(request.getNome());
        cpfService.validarCpf(request.getCpf());
        dataService.validarDataNascimento(request.getDataNascimento());
        telefoneService.validarNumeroTelefone(request.getTelefone());

        EnderecoDto enderecoDto = cepService.buscaEnderecoPor(request.getCep());
        System.out.println(enderecoDto);

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .senha(request.getSenha())
                .tipoUsuario(TipoUsuario.FREELANCER)
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

        Freelancer freelancer = Freelancer.builder()
                .usuario(usuario)
                .nome(request.getNome())
                .cpf(request.getCpf())
                .dataNascimento(dataService.converterParaLocalDate(request.getDataNascimento()))
                .telefone(request.getTelefone())
                .endereco(endereco)
                .descricao(request.getDescricao())
                .disponibilidade(request.getDisponibilidade())
                .dataCriacao(dataService.converterLocalDateTimeAtualParaString())
                .status(Status.ATIVO)
                .habilidades(request.getHabilidades())
                .build();

        freelancerJdbcTemplateDaoImpl.salvarDadosCadastrais(freelancer);

        System.out.println(freelancer);




    }

}
