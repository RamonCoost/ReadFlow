# 📚 ReadFlow API

O **ReadFlow** é uma aplicação de gerenciamento de leituras desenvolvida para ajudar leitores a organizar seus livros, acompanhar o progresso de leitura e manter um histórico de suas jornadas literárias.

A aplicação permite cadastrar livros, acompanhar páginas lidas, organizar leituras por status e gerenciar uma biblioteca pessoal de maneira simples e intuitiva.

O projeto foi desenvolvido como parte da minha formação em Engenharia de Software, com o objetivo de aplicar conhecimentos de desenvolvimento full stack, construção de APIs REST, persistência de dados, autenticação e integração entre sistemas.

## 💡 Sobre o projeto

O ReadFlow é composto por duas aplicações:

- **Backend:** API REST desenvolvida com Java e Spring Boot, responsável pelas regras de negócio, autenticação, segurança e persistência dos dados.
- **Frontend:** interface web desenvolvida com Angular, responsável pela interação do usuário com sua biblioteca pessoal.

Cada usuário possui sua própria biblioteca, protegida por autenticação, permitindo uma experiência individualizada.

## 🚀 Funcionalidades

### 📖 Gerenciamento de livros

- Cadastrar livros manualmente, informando título, autor e quantidade de páginas.
- Pesquisar livros em uma API externa e adicioná-los à biblioteca aproveitando informações como título, autor e quantidade de páginas.
- Consultar os livros cadastrados na biblioteca pessoal.
- Pesquisar livros da biblioteca por título, autor ou identificador na interface.
- Editar informações dos livros.
- Atualizar o progresso de leitura.
- Alterar manualmente o status de leitura.
- Excluir livros da biblioteca.
- Consultar livros utilizando paginação.

### 👤 Usuários e segurança

- Cadastro de usuários com senha protegida.
- Autenticação utilizando JWT (JSON Web Token).
- Confirmação de endereço de e-mail por link enviado ao usuário.
- Reenvio do link de confirmação.
- Acesso individualizado aos livros de cada usuário.
- Proteção dos endpoints privados por autenticação.

## 📚 Regras de negócio

O ReadFlow determina automaticamente o status de um livro conforme o progresso registrado.

| Status | Condição |
|---|---|
| `QUERO_LER` | Nenhuma página foi lida. |
| `LENDO` | O livro possui páginas lidas, mas ainda não foi concluído. |
| `CONCLUIDO` | A quantidade de páginas lidas é igual ao total de páginas. |
| `ABANDONEI` | Status definido manualmente pelo usuário. |

A aplicação valida o progresso registrado, impedindo que a quantidade de páginas lidas ultrapasse o total de páginas do livro. Cada livro pertence ao usuário responsável por seu cadastro, mantendo as bibliotecas individuais.

## 🛠️ Tecnologias utilizadas

O backend foi desenvolvido com **Java 21 e Spring Boot**.

| Tecnologia | Utilização |
|---|---|
| Java 21 | Linguagem principal do backend |
| Spring Boot | Desenvolvimento e configuração da aplicação |
| Spring Web | Criação dos endpoints REST |
| Spring Data JPA | Persistência e acesso aos dados |
| Spring Security | Autenticação e autorização |
| JWT | Autenticação baseada em tokens |
| Spring Validation | Validação dos dados recebidos |
| Spring Mail | Envio de e-mails de confirmação |
| Spring RestClient | Comunicação com API externa de livros |
| PostgreSQL 17 | Banco de dados relacional |
| Docker Compose | Execução dos serviços em contêineres |
| Gradle | Gerenciamento de dependências e build |
| Lombok | Redução de código repetitivo |
| MapStruct | Conversão entre entidades e DTOs |
| Swagger / OpenAPI | Documentação interativa dos endpoints |

## 🏗️ Arquitetura

O ReadFlow utiliza uma **arquitetura em camadas**, separando responsabilidades para facilitar a manutenção e evolução da aplicação.

### Organização do backend

- **Controller:** recebe requisições HTTP e disponibiliza os endpoints.
- **Service:** concentra regras de negócio, validações e operações.
- **Repository:** realiza acesso aos dados com Spring Data JPA.
- **Entity:** representa as entidades persistidas no banco de dados.
- **DTO:** define os dados recebidos e retornados pela API.
- **Mapper:** converte entidades e DTOs.
- **Security:** gerencia a autenticação JWT e a proteção dos endpoints.

### 🔗 Integração entre sistemas

O frontend Angular se comunica com o backend Spring Boot por HTTP. O backend aplica as regras de negócio e acessa o PostgreSQL.

Para a pesquisa de livros, o backend utiliza o `RestClient` para consultar uma API externa e disponibilizar os resultados ao frontend. O usuário pode selecionar uma obra e aproveitar suas informações no cadastro, sem preencher todos os campos manualmente.

### 🔐 Segurança

A API utiliza Spring Security e JWT para proteger recursos privados. Após a autenticação, o token JWT é enviado nas requisições aos endpoints protegidos.

Os livros são associados aos respectivos usuários, mantendo as bibliotecas individuais. O cadastro também possui confirmação de e-mail por token temporário e possibilidade de solicitar um novo link.

## 📌 Endpoints da API

A API utiliza JSON para comunicação entre frontend e backend. Endpoints privados exigem um token JWT no cabeçalho:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

### 📚 Gerenciamento de livros

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/livros` | Cadastra um livro na biblioteca do usuário |
| `GET` | `/livros` | Lista livros do usuário com paginação |
| `GET` | `/livros/{id}` | Busca um livro pelo identificador |
| `GET` | `/livros?autor=nome` | Filtra livros pelo autor |
| `PUT` | `/livros/{id}` | Atualiza informações de um livro |
| `PUT` | `/livros/{id}/progresso` | Atualiza o progresso de leitura |
| `PUT` | `/livros/{id}/status` | Altera manualmente o status de leitura |
| `DELETE` | `/livros/{id}` | Remove um livro da biblioteca |

### 📄 Paginação

A listagem utiliza `Pageable` do Spring Data. Exemplo:

```http
GET /livros?page=0&size=5
```

A resposta inclui os livros da página solicitada e metadados, como quantidade total de elementos, número de páginas e indicadores de primeira e última página.

### 👤 Usuários e autenticação

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/usuario` | Cadastra um usuário |
| `POST` | `/auth/login` | Autentica o usuário |
| `PUT` | `/usuario` | Atualiza informações do usuário |
| `PATCH` | `/usuario/email?token=...` | Confirma o endereço de e-mail |
| `POST` | `/usuario/reenviar-email?email=...` | Solicita um novo link de confirmação |

### 🔎 Pesquisa externa de livros

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/pesquisa?termo=nome` | Pesquisa livros em uma API externa |

Exemplo:

```http
GET /pesquisa?termo=harry%20potter
```

A resposta contém uma lista de livros encontrados, que pode ser utilizada no fluxo de cadastro da biblioteca.

### 🩺 Monitoramento

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/health` | Verifica a disponibilidade da aplicação |

### 📖 Documentação interativa

Com a API em execução localmente, acesse o Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

A interface permite consultar os endpoints, visualizar os contratos das requisições e testar as operações da API.

## ▶️ Como executar o projeto

O Docker Compose inicia a API Spring Boot e o PostgreSQL.

### Pré-requisitos

- Git
- Docker
- Docker Compose

Para desenvolvimento fora dos contêineres, também é necessário ter o Java 21 instalado.

### 1. Clonar o repositório

```bash
git clone https://github.com/ramoncoost/ReadFlow.git
cd ReadFlow
```

> Caso o repositório backend utilize outro endereço, substitua a URL acima pela URL exibida no GitHub.

### 2. Configurar as variáveis de ambiente

Copie o modelo disponível na raiz do projeto:

```bash
cp .env.example .env
```

Preencha os valores necessários:

| Variável | Finalidade |
|---|---|
| `KEY_POSTGRES_DB` | Nome do banco de dados |
| `KEY_POSTGRES_USER` | Usuário do PostgreSQL |
| `KEY_POSTGRES_PASSWORD` | Senha do PostgreSQL |
| `KEY_POSTGRES_HOST` | Endereço do banco de dados |
| `KEY_POSTGRES_PORT` | Porta do PostgreSQL |
| `JWT_SECRET` | Chave de assinatura dos tokens JWT |
| `JWT_EXPIRATION_MS` | Expiração dos tokens em milissegundos |
| `KEY_API` | Chave de acesso ao serviço externo de livros |
| `EMAIL_USERNAME` | Conta utilizada para envio de e-mails |
| `EMAIL_PASSWORD` | Credencial do serviço de e-mail |
| `CORS_ALLOWED_ORIGINS` | Origens autorizadas a acessar a API |

**Atenção:** não compartilhe o `.env` nem envie credenciais reais ao GitHub. No Docker Compose, o host do PostgreSQL é configurado como `db` para o serviço backend.

### 3. Iniciar os serviços

Na raiz do projeto:

```bash
docker compose up -d --build
```

O Compose configura:

- **PostgreSQL 17:** porta `5432`, com volume persistente `postgres_data`.
- **Spring Boot:** porta `8080`.

### 4. Verificar a aplicação

**Health check:**

```http
GET http://localhost:8080/health
```

Resposta esperada:

```json
{
  "status": "UP"
}
```

**Swagger UI:**

```text
http://localhost:8080/swagger-ui/index.html
```

### 5. Encerrar os serviços

```bash
docker compose down
```

O volume do PostgreSQL é preservado ao encerrar os serviços com esse comando.

## 🔮 Evolução do projeto

O ReadFlow está em desenvolvimento contínuo, com novas funcionalidades e melhorias planejadas:

- Implementar um sistema de avaliação de livros de **1 a 5 estrelas**, permitindo atribuir notas às obras lidas.
- Aprimorar o desempenho das consultas e da listagem de livros.
- Expandir as funcionalidades de acompanhamento de leitura e estatísticas.
- Fortalecer os mecanismos de segurança e tratamento de erros.
- Evoluir a integração com serviços externos.

## 🌐 Frontend

O ReadFlow possui uma interface web desenvolvida com **Angular e Angular Material**, permitindo gerenciar bibliotecas de forma visual e intuitiva.

**Repositório:** [ReadFlow Frontend](https://github.com/ramoncoost/ReadFlow-front)

## 👨‍💻 Autor

Desenvolvido por **Ramon Costa**, como parte de sua formação em Engenharia de Software e de seu desenvolvimento profissional na área de tecnologia.

**GitHub:** [ramoncoost](https://github.com/ramoncoost)
