# Zup Backend as a Service
Esta aplicação permite o CRUD de objetos de forma simples, sem a necessidade de criar esquemas de banco de dados. 
### Sobre a aplicação
Foi utilizada a linguagem **Java** com o framework **Spring** pela facilidade em criar e manter o projeto. 
O banco de dados é o **MongoDB**, um banco não relacional (orientado a documento), essa escolha foi feita devido a facilidade em trabalhar com os dados em json e pela flexibilidade que este tipo de banco possui, permitindo criar e modificar os documentos no banco de forma bastante simples e rápida. Neste tipo de aplicação não é comum o uso de consultas muito avançadas, o que reforça a escolha deste tipo de banco de dados.
### Como Usar
Este software foi criado utilizando um banco de dados em memória, o que significa que não é necessário configurar um banco de dados para utiliza-lo (visando facilitar seu uso, mas é bastante simples configura-lo para usar um banco em disco caso seja necessário), entretanto os dados desaparecerão quando a aplicação for encerrada. 
##### Iniciando a aplicação
Para iniciar a aplicação basta executar o run.sh na raiz do projeto.
```sh
$ ./run.sh
```
A aplicação estará rodando na porta 8080 no servidor local.
##### Fazendo requisições
A aplicação permite as requisições básicas de CRUD da sequinte maneira:
* GET /xxx - Lista todos os elementos do Modelo XXX
* GET /xxx/{id} - Busca um registro do modelo XXX por id
* DELETE /xxx/{id} - Deleta um registo do modelo XXX
* POST /xxx - Cria um novo registro do modelo XXX

    {"prop1":"value1", "prop2":"value2"}
* PUT /xxx/{id} - Edita um registro do modelo XXX

    {"prop1":"value1", "prop2":"value2"}

Observações: 
* Ao inserir um objeto no banco (POST), uma coleção será criada automaticamente sem necessidade de defini-la previamente; 
* Ao inserir um objeto no banco (POST), não é preciso conter as mesmas propriedades dos outros objetos, podendo inserir novas ou ignorar pripriedades já inseridas no passado;
* Ao Editar um objeto, é possível adicionar e/ou ignorar propriedades que esse objeto possuia, desde que o ID seja enviado o objeto correspondente pode ser completamente alterado.
### Testes
A aplicação possui testes unitários, testes de integração e testes funcionais.
Para rodar os testes basta executar o seguinte comando: 
```sh
$ ./run-tests.sh
```
### Exemplos de uso
Testes realizados utilizando o POSTMAN:
###### POST
http://localhost:8080/user

Body:
```json
{
    "name":"João",
	"age":18
}
```
Resposta:
```json
{ "_id" : { "$oid" : "595aad7e687e7b0abe27cb13" }, "name" : "João", "age" : 18 }
```
StatusCode: 201 CREATED
###### GET
http://localhost:8080/user/595aad7e687e7b0abe27cb13

Resposta:
```json
{ "_id" : { "$oid" : "595aad7e687e7b0abe27cb13" }, "name" : "João", "age" : 18}
```
StatusCode: 200 OK
###### PUT
http://localhost:8080/user/595aad7e687e7b0abe27cb13

Body:
```json
{
	"name":"João",
	"age":20,
	"email":"joao@email.com"
}
```

Resposta:

StatusCode: 200 OK
###### GET
http://localhost:8080/user

Resposta:
```json
[
    { "_id" : { "$oid" : "595aad7e687e7b0abe27cb13" }, "name" : "João", "age" : 20, "email" : "joao@email.com" }
]
```
StatusCode: 200 OK
