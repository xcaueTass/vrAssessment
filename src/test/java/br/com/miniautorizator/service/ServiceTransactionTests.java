package br.com.miniautorizator.service;

import br.com.miniautorizator.card.entities.CardEntity;
import br.com.miniautorizator.card.exceptions.CardException;
import br.com.miniautorizator.card.repositories.CardRepository;
import br.com.miniautorizator.card.request.RegisterCard;
import br.com.miniautorizator.card.response.ResponseCard;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ServiceTransactionTests {

    @InjectMocks
    ServiceTransaction serviceTransaction;

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
    @DisplayName("Nao deve retornar nulo")
    void must_not_be_null_transaction() {

        CardEntity cardEntitys = CardEntity.builder().id((long) 1).cardNumber(cardNumber).password(password)
                .valueCard(500.00).build();

        registerCard = RegisterCard.builder().cardNumber(cardNumber).password(password).valueCard(20.00).build();

        Optional<CardEntity> optionalResponse = Optional.of(cardEntitys);
        when(cardRepository.findCardAndPassword(cardNumber, password)).thenReturn(optionalResponse);

        // validação
        assertNotNull(serviceTransaction.cardTransaction(registerCard));
    }

    @Test
    @DisplayName("Deve retornar saldo insuficiente")
    void must_be_insufficient_balance() {

        CardEntity cardEntitys = CardEntity.builder().id((long) 1).cardNumber(cardNumber).password(password)
                .valueCard(500.00).build();

        registerCard = RegisterCard.builder().cardNumber(cardNumber).password(password).valueCard(520.00).build();

        Optional<CardEntity> optionalResponse = Optional.of(cardEntitys);
        when(cardRepository.findCardAndPassword(cardNumber, password)).thenReturn(optionalResponse);

        // validação
        assertThrows(CardException.class, () -> serviceTransaction.cardTransaction(registerCard));
    }

    @Test
    @DisplayName("Deve retornar cartao inexistente")
    void must_be_card_nonexistent() {

        CardEntity cardEntitys = CardEntity.builder().id((long) 1).cardNumber(cardNumber).password(password)
                .valueCard(500.00).build();

        registerCard = RegisterCard.builder().cardNumber(cardNumber).password(password).valueCard(520.00).build();

        Optional<CardEntity> optionalResponse = Optional.of(cardEntitys);
        when(cardRepository.findCard(cardNumber)).thenReturn(optionalResponse);

        // validação
        assertThrows(CardException.class, () -> serviceTransaction.cardTransaction(registerCard));
    }

}
