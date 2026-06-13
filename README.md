# ReadFlow API

API REST desenvolvida com Java e Spring Boot para gerenciamento de leitura de livros.

## 🚀 Funcionalidades

* Cadastro de livros
* Listagem de livros
* Busca por ID
* Busca por autor
* Atualização do progresso de leitura
* Atualização manual do status de leitura
* Exclusão de livros

## 📚 Regras de negócio

O sistema calcula automaticamente o status de leitura com base no progresso:

* `QUERO_LER` → quando páginas lidas = 0
* `LENDO` → quando páginas lidas > 0 e menor que o total
* `CONCLUIDO` → quando páginas lidas = total de páginas

Também é possível atualizar manualmente o status para:

* `ABANDONEI`

## 🛠 Tecnologias utilizadas

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Validation
* Lombok
* PostgreSQL
* Gradle

## 📌 Endpoints principais

### Criar livro

`POST /livros`

### Listar livros

`GET /livros`

### Buscar livro por ID

`GET /livros/{id}`

### Buscar livro por autor

`GET /livros/autor?autor=machado`

### Atualizar progresso de leitura

`PUT /livros/{id}/progresso`

### Atualizar status de leitura

`PUT /livros/{id}/status`

### Deletar livro

`DELETE /livros/{id}`

---

## ▶ Como executar o projeto

Clone o repositório:

```bash
git clone <LINK_DO_REPOSITORIO>
```

Entre na pasta do projeto:

```bash
cd ReadFlow
```

Execute o projeto:

```bash
./gradlew bootRun
```

A aplicação estará disponível em:

```bash
http://localhost:8080
```

## 📖 Próximos passos

* Desenvolvimento do frontend com Angular
* Integração completa entre frontend e backend
* Melhorias na documentação
* Paginação e filtros
* Autenticação com JWT

## 👨‍💻 Autor

Desenvolvido por Ramon.
