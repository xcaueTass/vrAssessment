package br.com.miniautorizator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.miniautorizator.card.request.RegisterCard;
import br.com.miniautorizator.service.ServiceCard;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/cartoes")
@Slf4j
public class CardController {

	@Autowired
	ServiceCard serviceCard;

	@PostMapping
	public ResponseEntity<RegisterCard> register(@Validated @RequestBody RegisterCard registerCard) {

		log.info("[INICIO] - CardController - Registrando novo cartão");
		var response = serviceCard.cardRegister(registerCard);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/transacao")
	public ResponseEntity<RegisterCard> transactions(@Validated @RequestBody RegisterCard registerCard) {

		log.info("[INICIO] - CardController - Efetuando uma transação");
		serviceCard.cardTransaction(registerCard);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping(path = { "/{numeroCartao}" })
	public ResponseEntity<RegisterCard> searchTcc(@PathVariable String numeroCartao) {

		log.info("[INICIO] - Consultando cartao: {}", numeroCartao);
		return ResponseEntity.status(HttpStatus.OK).body(serviceCard.cardBalance(numeroCartao));
	}
}
