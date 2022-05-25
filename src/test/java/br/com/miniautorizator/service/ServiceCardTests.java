package br.com.miniautorizator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import br.com.miniautorizator.card.exceptions.DataBaseExceptions;
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
class ServiceCardTests {

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
    void must_register_card() {

        cardRepository.deleteById((long) 1234567);

        var expected = "Cartão salvo com sucesso";
        // validação
        assertEquals(expected, serviceCard.cardRegister(registerCard));
    }

    @Test
    @DisplayName("Numero do cartao nao pode ser nulo")
    void card_must_not_null() {

        cardRepository.deleteById((long) 1234567);
        cardNumber = "";
        password = "1234";

        registerCard = RegisterCard.builder().cardNumber(cardNumber).password(password).build();

        // validação
        assertThrows(CardException.class, () -> serviceCard.cardRegister(registerCard));

    }

    @Test
    @DisplayName("Password nao pode ser nulo")
    void password_must_not_null() {

        cardRepository.deleteById((long) 1234567);
        cardNumber = "6549873025634501";
        password = "";

        registerCard = RegisterCard.builder().cardNumber(cardNumber).password(password).build();

        // validação
        assertThrows(CardException.class, () -> serviceCard.cardRegister(registerCard));

    }

    @Test
    @DisplayName("Retorno response registrado com sucesso")
    void must_exist_card() {

        CardEntity cardEntitys = CardEntity.builder().id((long) 1234567).cardNumber(cardNumber).password(password)
                .valueCard(500.00).build();

        Optional<CardEntity> optionalResponse = Optional.of(cardEntitys);

        when(cardRepository.findCard(cardNumber)).thenReturn(optionalResponse);

        var expected = "Cartão ja cadastrado";
        // validação
        assertEquals(expected, serviceCard.cardRegister(registerCard));
    }

    @Test
    @DisplayName("Nao deve retornar nulo o saldo")
    void must_not_be_null_balance() throws DataBaseExceptions {

        CardEntity cardEntitys = CardEntity.builder().id((long) 1234567).cardNumber(cardNumber).password(password)
                .valueCard(500.00).build();

        Optional<CardEntity> optionalResponse = Optional.of(cardEntitys);

        when(cardRepository.findCard(cardNumber)).thenReturn(optionalResponse);

        // validação
        assertNotNull(serviceCard.cardBalance(cardNumber));
    }

    @Test
    @DisplayName("Deve existir cartao na base")
    void card_must_exist() {

        cardNumber = "";
        // validação
        assertThrows(DataBaseExceptions.class, () -> serviceCard.cardBalance(cardNumber));
    }


}
