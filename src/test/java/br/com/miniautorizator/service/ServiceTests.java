package br.com.miniautorizator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.miniautorizator.card.entities.CardEntity;
import br.com.miniautorizator.card.exceptions.CardException;
import br.com.miniautorizator.card.repositories.CardRepository;
import br.com.miniautorizator.card.request.RegisterCard;

@SpringBootTest
class ServiceTests {

	@InjectMocks
	ServiceCard serviceCard;

	@Mock
	CardRepository cardRepository;

	@Mock
	RegisterCard registerCard;

	String cardNumber;
	String password;

	@BeforeEach
	void setUp() {

		cardNumber = "6549873025634501";
		password = "1234";

		registerCard = RegisterCard.builder().cardNumber(cardNumber).password(password).build();

	}

	@Test
	@DisplayName("Retorno response registrado com sucesso")
	void must_register_card() throws CardException {

		cardRepository.deleteById((long) 1234567);

		var expected = "Cartão salvo com sucesso";
		// validação
		assertEquals(expected, serviceCard.cardRegister(registerCard));
	}

	@Test
	@DisplayName("Retorno response registrado com sucesso")
	void must_exist_card() throws CardException {

		CardEntity cardEntitys = CardEntity.builder().id((long) 1234567).cardNumber(cardNumber).password(password)
				.valueCard(500.00).build();

		Optional<CardEntity> optionalResponse = Optional.of(cardEntitys);

		when(cardRepository.findCard(cardNumber)).thenReturn(optionalResponse);

		var expected = "Cartão ja cadastrado";
		// validação
		assertEquals(expected, serviceCard.cardRegister(registerCard));
	}


	@Test
	@DisplayName("Nao deve retornar nulo")
	void must_not_be_null_balance() throws CardException {

		CardEntity cardEntitys = CardEntity.builder().id((long) 1234567).cardNumber(cardNumber).password(password)
				.valueCard(500.00).build();

		Optional<CardEntity> optionalResponse = Optional.of(cardEntitys);

		when(cardRepository.findCard(cardNumber)).thenReturn(optionalResponse);

		// validação
		assertNotNull(serviceCard.cardBalance(cardNumber));
	}

	@Test
	@DisplayName("Nao deve retornar nulo")
	void must_not_be_null_transaction() throws CardException {

		CardEntity cardEntitys = CardEntity.builder().id((long) 1).cardNumber(cardNumber).password(password)
				.valueCard(500.00).build();

		registerCard = RegisterCard.builder().cardNumber(cardNumber).password(password).valueCard(20.00).build();

		Optional<CardEntity> optionalResponse = Optional.of(cardEntitys);
		when(cardRepository.findCardAndPassword(cardNumber, password)).thenReturn(optionalResponse);

		// validação
		assertNotNull(serviceCard.cardTransaction(registerCard));
	}

	@Test
	@DisplayName("Deve retornar saldo insuficiente")
	void must_be_insufficient_balance() throws CardException {

		CardEntity cardEntitys = CardEntity.builder().id((long) 1).cardNumber(cardNumber).password(password)
				.valueCard(500.00).build();

		registerCard = RegisterCard.builder().cardNumber(cardNumber).password(password).valueCard(520.00).build();

		Optional<CardEntity> optionalResponse = Optional.of(cardEntitys);
		when(cardRepository.findCardAndPassword(cardNumber, password)).thenReturn(optionalResponse);

		// validação
		assertThrows(CardException.class, () -> serviceCard.cardTransaction(registerCard));
	}

	@Test
	@DisplayName("Deve retornar cartao inexistente")
	void must_be_card_nonexistent() throws CardException {

		CardEntity cardEntitys = CardEntity.builder().id((long) 1).cardNumber(cardNumber).password(password)
				.valueCard(500.00).build();

		registerCard = RegisterCard.builder().cardNumber(cardNumber).password(password).valueCard(520.00).build();

		Optional<CardEntity> optionalResponse = Optional.of(cardEntitys);
		when(cardRepository.findCard(cardNumber)).thenReturn(optionalResponse);

		// validação
		assertThrows(CardException.class, () -> serviceCard.cardTransaction(registerCard));
	}

}
