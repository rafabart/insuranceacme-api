### Sobre a API

Aplicação de orquestramento de contações de seguros.

### Ferramentas usadas
* IntelliJ IDEA;
* Java 17;
* Spring;
* Kafka
* MongoDB;
* Lombok
* JUnit;
* TestContainer;
* Mockito;
* Docker;

### Rodar testes unitários e de integração
Para rodar os teste utilize o comando abaixo na raiz do projeto:

```./gradlew clean test```

### Rodar a aplicação
Para rodar a api utilize o comando abaixo na raiz do projeto:

```./gradlew bootRun```

### Acessar serviços do docker:
[Kafdrop](http://localhost:19000/) - permite visualizar as mensagem nos tópicos kafka   
[Mongo Express](http://localhost:8091/db/insurance/) - permite visualizar dos dados na base mongo
OBS: Para acessar usar o user: "admin"; e a senha "1234"

### Usando a aplicação
Para usar a api será necessário baixar e instalar o [insomnia](https://insomnia.rest/download) ou outro similar.  
Importe os Curls dos serviços abaixo para o insomnia:

* Criar nova cotação:  
  ``curl --request POST \
  --url http://localhost:8080/Insurancequotes \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/10.2.0' \
  --data '{
	"product_id": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
	"offer_id": "adc56d77-348c-4bf0-908f-22d402ee715c",
	"category": "HOME",
	"total_monthly_premium_amount": 75.25,
	"total_coverage_amount": 825000.00,
	"coverages": {
		"Incêndio": 250000.00,
		"Desastres naturais": 500000.00,
		"Responsabiliadade civil": 75000.00
	},
	"assistances": [
		"Encanador",
		"Eletricista",
		"Chaveiro 24h"
	],
	"customer": {
		"document_number": "36205578900",
		"name": "John Wick",
		"type": "NATURAL",
		"gender": "MALE",
		"date_of_birth": "1973-05-02",
		"email": "johnwick@gmail.com",
		"phone_number": 11950503030
	}
}'``


* Buscar cotação pelo id:  
``curl --request GET \
  --url http://localhost:8080/Insurancequotes/1734453707796 \
  --header 'User-Agent: insomnia/10.2.0'``

* Listar todas as contações paginadas:  
``curl --request GET \
  --url 'http://localhost:8080/Insurancequotes?page=0&size=10' \
  --header 'User-Agent: insomnia/10.2.0'``


* Serviço auxiliar, para enviar uma mensagem para o tópico kafka 'insurance-policy-created'  
com um ID(insurance_policy_id) para consumo da api, simulando assim a integração que envia o insurance_policy_id 
que atualizara a cotação na base de dados:  
OBS: informar no campo "id" do corpo da requisição o id da contação salva na base de dados 
* ``curl --request POST \
  --url http://localhost:8080/helper/kafka/insurance-policy-created \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/10.2.0' \
  --data '{
	"id": 1734453707796,
	"insurance_policy_id": 756969,
	"category": "HOME",
	"coverages": {
		"Incêndio": 250000.0,
		"Responsabiliadade civil": 75000.0,
		"Desastres naturais": 500000.0
	},
	"assistances": [
		"Encanador",
		"Eletricista",
		"Chaveiro 24h"
	],
	"customer": {
		"name": "John Wick",
		"type": "NATURAL",
		"gender": "MALE",
		"email": "johnwick@gmail.com",
		"document_number": "36205578900",
		"date_of_birth": "1973-05-02",
		"phone_number": 11950503030
	},
	"product_id": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
	"offer_id": "adc56d77-348c-4bf0-908f-22d402ee715c",
	"total_monthly_premium_amount": 75.25,
	"total_coverage_amount": 825000.0
}'``