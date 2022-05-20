# vrAssessment
Avaliação VR

### Aplicação efetua alguns testes referente a pagamentos

- Para iniciar o projeto basta importar para alguma IDE e rodar o projeto

- Para acessar o banco de dados basta acessar: http://localhost:8081/h2-console/
Login: admin - senha: admin

- O sitema existe 3 requisições: Cadastro de cartao, ver saldo de cartao, e atualizar saldo do cartao

#### Exemplos de requisição:
POST
http://localhost:8081/cartoes/
{
"card_number": "6549873025634502",
"password": "1234"
}

GET
http://localhost:8081/cartoes/6549873025634501

POST
http://localhost:8081/cartoes/transacao
{
"card_number": "6549873025634502",
"password": "1234",
"value_card": 9.00
}

- Link para validar swagger local: http://localhost:8081/swagger-ui/index.html#/
- 