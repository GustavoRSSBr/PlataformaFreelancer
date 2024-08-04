# Plataforma Freelancer

## Descrição do Projeto

A **Plataforma Freelancer** é uma aplicação desenvolvida para conectar freelancers a empresas em busca de serviços especializados. A plataforma permite que freelancers se cadastrem, completem seus perfis, busquem por trabalhos disponíveis, e que empresas publiquem projetos e contratem profissionais de forma rápida e eficiente. O projeto disponibilizado pela empresa só será considerado concluído quando houver a avaliação final do projeto por ambas as partes (Freelancer e Empresa) garantindo um sistema de reputação confiável.

## Estrutura de Diretórios

```
/PlataformaFreelancer
│
├── /fourcamp/
│   ├── /fourcamp/
│   │   ├── /src/
│   │   │   ├── /main/
│   │   │   │   ├── /java/
│   │   │   │   │   ├── br/com/plataformafreelancer/fourcamp/
│   │   │   │   │   │   ├── controllers/   # Contém os controladores da aplicação
│   │   │   │   │   │   ├── exceptions/    # Exceções personalizadas
│   │   │   │   │   │   ├── model/         # Modelos de dados
│   │   │   │   │   │   ├── usecase/       # Lógica de negócios e serviços
│   │   │   │   │   │   ├── repository/    # Repositórios de dados
│   │   │   │   │   │   ├── utils/         # Utilitários e serviços auxiliares
│   │   │   │   └── /resources/
│   │   │   │       └── application.properties  # Configurações da aplicação
│   │   │   └── /test/  # Testes unitários e de integração
│   └── /target/  # Arquivos compilados e gerados pelo Maven
```

## Instalação

### Pré-requisitos

- [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/)

### Passos para Instalação

1. **Clonar o Repositório**

   ```bash
   git clone <https://github.com/GustavoRSSBr/PlataformaFreelancer.git>
   cd PlataformaFreelancer
   ```

2. **Configurar o Banco de Dados**

   - O projeto utiliza um banco de dados que está em outro repositório. Certifique-se de cloná-lo e configurá-lo corretamente. Você pode encontrar o repositório do banco de dados em: [Link para o Repositório do Banco de Dados](https://github.com/GustavoRSSBr/PlataformaFreelancerBaseDeDados.git)

   - Após configurar o banco de dados, insira as credenciais no arquivo `application.properties`.

3. **Compilar e Executar a Aplicação**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Acessar a Aplicação**

   - A aplicação estará disponível em `http://localhost:8080`.

## Como Usar


Para mais detalhes sobre os endpoints, acesse o Swagger UI em `http://localhost:8080/swagger-ui/index.html#/`.

## Funcionalidades Principais

- Cadastro de usuários (freelancers e empresas)
- Publicação e gerenciamento de projetos
- Avaliação de freelancers e empresas para a conclusão do projeto
- Apenas um freelancer pode ser associado a um projeto para garantir uma avaliação assertiva
- Sistema de busca de projetos com a opção de listar projetos por habilidades compatíveis
- Diversas validações de dados:
  - Nome
  - Email
  - CPF 
  - CNPJ
  - Telefone
  - Chamada da API ViaCEP para validação de endereços

## Contribuições

Para contribuir com este projeto:

1. Faça um fork do repositório.
2. Crie uma nova branch (`git checkout -b feature/nova-feature`).
3. Commit suas alterações (`git commit -m 'Adiciona nova feature'`).
4. Faça o push para a branch (`git push origin feature/nova-feature`).
5. Abra um Pull Request.