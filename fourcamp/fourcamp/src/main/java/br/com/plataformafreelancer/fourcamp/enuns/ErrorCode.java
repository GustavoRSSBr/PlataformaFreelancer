package br.com.plataformafreelancer.fourcamp.enuns;

import lombok.Getter;

@Getter
public enum ErrorCode {
    AVALIACAO_EXISTE("Avaliação já existe", "Avaliação já existe"),
    EMPRESA_NAO_EXISTE("Empresa não existe", "Empresa não existe"),
    FREELANCER_NAO_EXISTE("Freelancer não existe", "Freelancer não existe"),
    PROJETO_NAO_EXISTE("Projeto não existe", "Projeto não existe"),
    FREELANCER_NAO_ASSOCIADO("Freelancer não está associado ao projeto", "Freelancer não está associado ao projeto"),
    EMPRESA_NAO_ASSOCIADA("Empresa não está associada ao projeto", "Empresa não está associada ao projeto"),
    NOTA_INVALIDA("Nota deve ser entre 1 e 5", "Nota deve ser entre 1 e 5"),
    EMAIL_JA_CADASTRADO("Email", "Email já está cadastrado"),
    CPF_JA_CADASTRADO("CPF", "CPF já está cadastrado"),
    CNPJ_JA_CADASTRADO("CNPJ", "CNPJ já está cadastrado"),
    ID_EMPRESA_NAO_EXISTE("ID de Empresa", "ID de Empresa não existe"),
    ID_PROJETO_NAO_EXISTE("ID de Projeto", "ID de Projeto não existe"),
    ID_FREELANCER_NAO_EXISTE("ID de Freelancer", "ID de Freelancer não existe"),
    PROPOSTA_JA_ACEITA("Proposta já foi aceita e não pode ser recusada", "Proposta já foi aceita e não pode ser recusada"),
    PROPOSTA_NAO_EXISTE("Proposta com id", "Proposta com id não existe"),
    PROJETO_JA_ASSOCIADO("Projeto já possui um freelancer associado", "Projeto já possui um freelancer associado"),
    CEP_INVALIDO("CEP inválido", "CEP inválido: "),
    CEP_NAO_ENCONTRADO("CEP não encontrado", "CEP não encontrado: "),
    CNPJ_INVALIDO("CNPJ inválido", "CNPJ inválido: "),
    CPF_INVALIDO("CPF inválido", "CPF inválido: "),
    EMAIL_INVALIDO("Email inválido", "Email inválido: "),
    NOME_INVALIDO("Nome inválido", "Nome inválido: "),
    SENHA_INVALIDA("Senha inválida", "Senha inválida: a senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula, um número e um caractere especial."),
    TELEFONE_INVALIDO("Número de telefone inválido", "Número de telefone inválido: "),
    OUTRO_ERRO("Outro erro", "Erro desconhecido");

    private final String message;
    private final String customMessage;

    ErrorCode(String message, String customMessage) {
        this.message = message;
        this.customMessage = customMessage;
    }

    public static ErrorCode fromMessage(String message) {
        for (ErrorCode code : ErrorCode.values()) {
            if (message.contains(code.getMessage())) {
                return code;
            }
        }
        return OUTRO_ERRO;
    }
}
