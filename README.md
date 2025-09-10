# Quarkus JWT Auth API

Este projeto é uma API RESTful desenvolvida com Quarkus, Hibernate Panache, JWT e MySQL, que gerencia usuários, autenticação e roles (ADMIN e BASIC).

---

## Tecnologias utilizadas

- Java 21
- Quarkus
- MySQL
- JWT

---

## Endpoints

### Auth

- POST /v1/auth/register  
  Registra um novo usuário.

Request body:
```json
  {
  "username": "usuario",
  "password": "senha123"
  }
```
  Response: 201 Created

- POST /v1/auth/login  
  Faz login e retorna JWT.

  Request body:
```json
  {
  "username": "usuario",
  "password": "senha123"
  }
```  
  Response:
```json
  {
  "accessToken": "<jwt-token>",
  "expiresIn": 300
  }
```
---

### Users

- GET /v1/users  
  Retorna lista paginada de usuários (somente ADMIN).

  Query params opcionais:
    - page → página (default: 0)
    - pageSize → tamanho da página (default: 10)

  Header: Authorization: Bearer <jwt-token>

- PUT /v1/users  
  Atualiza a senha do usuário logado (ADMIN ou BASIC).

  Request body:
```json
  {
  "oldPassword": "senhaAtual",
  "newPassword": "novaSenha123"
  }
```
  Header: Authorization: Bearer <jwt-token>  
  Response: 204 No Content

---

### Autenticação

- Utiliza JWT com claims:
    - sub → UUID do usuário
    - upn → username
    - groups → roles do usuário (ADMIN ou BASIC)

- Chaves de assinatura localizadas em resources:
    - privateKey.pem → usada para gerar token
    - publicKey.pem → usada para validar token

---

## Tratamento de erros

- Todas as exceptions customizadas retornam JSON padronizado via ExceptionMapper:

```json
  {
  "error": "Bad credentials",
  "message": "Usuário ou senha incorretos."
  }
```

- Status HTTP correspondentes:
    - 401 Unauthorized → BadCredentialsException
    - 409 Conflict → UserExistsException
    - 404 Not Found → UserNotExistsException
    - 500 Internal Server Error → outras exceptions

---

## Observações

- Sempre usar tokens válidos no header Authorization (Bearer <token>) para endpoints privados.
- As roles são definidas via enum Role.Values e armazenadas no JWT.
