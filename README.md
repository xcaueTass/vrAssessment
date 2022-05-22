# vrAssessment

Avaliação VR

### Aplicação efetua alguns testes referente a pagamentos

- Para iniciar o projeto basta importar para alguma IDE e rodar o projeto

- Para acessar o banco de dados basta acessar: http://localhost:8081/h2-console/
  Login: admin - senha: admin
- Para efetuar a exclusão da database fica localizado no diretorio: vrAssessment\database
  com nome de dbh2.mv.db
- O sitema existe 3 requisições: Cadastro de cartao, ver saldo de cartao, e atualizar saldo do cartao

### Informação das requisições:

#### POST - Cria novo cartao
- http://localhost:8081/cartoes/ - url

- {
  "card_number": "6549873025634502",
  "password": "1234"
  } - request (body)

- "card_number": "6549873025634502" - Numero do cartao que deseja cadastrar
- "password": "1234" - senha do cartao que deseja cadastrar

#### GET - Verifica saldo do cartao
- http://localhost:8081/cartoes/{numeroCartao} - url
- Exemplo: http://localhost:8081/cartoes/6549873025634501
- 6549873025634501 - Numero do cartao a ser consultado

#### POST
- http://localhost:8081/cartoes/transacao - url

- {
  "card_number": "6549873025634502",
  "password": "1234",
  "value_card": 9.00
  } - request (body)

- "card_number": "6549873025634502" - numero do cartao que sera descontado o valor
- "password": "1234" - senha do cartao
- "value_card": 9.00 - valor a ser descontado

#### Link para validar swagger local: http://localhost:8081/swagger-ui/index.html#/

