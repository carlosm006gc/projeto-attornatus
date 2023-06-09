﻿# projeto-attornatus
Este é um projeto de exemplo que consiste em uma API RESTful simples para gerenciamento de pessoas. A API permite criar, listar, atualizar e excluir pessoas e seus respectivos endereços.

## Tecnologias utilizadas

* Java 11

* Spring Boot 2.5.4

* JUnit 5

* Mockito

* H2 Database

* Maven

## Como executar

1. Certifique-se de ter o Java 11 instalado em sua máquina.

2. Faça o clone deste repositório em sua máquina.

3. Abra o terminal na pasta raiz do projeto.

4. Execute o comando mvn spring-boot:run para iniciar a aplicação.

5. Acesse a API através da URL http://localhost:8080.

## Endpoints disponíveis

### Pessoas

- GET /pessoas: lista todas as pessoas cadastradas.

- GET /pessoas/{id}: busca uma pessoa pelo ID.

- POST /pessoas: cadastra uma nova pessoa.

- PUT /pessoas/{id}: atualiza os dados de uma pessoa.

- DELETE /pessoas/{id}: exclui uma pessoa pelo ID.

### Endereços

- GET /pessoas/{pessoaId}/enderecos: lista todos os endereços de uma pessoa.

- POST /pessoas/{pessoaId}/enderecos: cadastra um novo endereço para uma pessoa.

- PUT /pessoas/{pessoaId}/enderecos/{enderecoId}/principal: define um endereço como o principal dentro de uma pessoa.

## Contribuição

Se você deseja contribuir com este projeto, basta seguir os seguintes passos:

- Faça um fork deste repositório.
- Crie uma nova branch com sua contribuição: git checkout -b minha-contribuicao.
- Faça as alterações desejadas.
- Commit suas alterações: git commit -m 'Minha contribuição'.
- Envie as alterações para o seu fork: git push origin minha-contribuicao.
- Abra um Pull Request para este repositório.

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo LICENSE.md para mais detalhes.
