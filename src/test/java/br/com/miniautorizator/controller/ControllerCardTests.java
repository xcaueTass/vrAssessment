package br.com.miniautorizator.controller;

import br.com.miniautorizator.service.ServiceTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.miniautorizator.card.entities.CardEntity;
import br.com.miniautorizator.card.repositories.CardRepository;
import br.com.miniautorizator.card.request.RegisterCard;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebMvcTest(controllers = TransactionController.class)
class ControllerCardTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ServiceTransaction serviceTransaction;

    @MockBean
    CardRepository cardRepository;

    private RegisterCard registerCard;
    private String URI = "/transacao";
    String numberCard = "6549873025634502";
    String password = "1234";


    @Test
    @DisplayName("Metodo POST /cartoes/transactions - Retorno response registrado com sucesso")
    void must_return_transaction() throws Exception {

        registerCard = RegisterCard.builder().cardNumber(numberCard).password(password).valueCard(400.00).build();
        var entity = CardEntity.builder().cardNumber(numberCard).password(password).valueCard(500.00).build();

        Mockito.when(cardRepository.findCard(numberCard)).thenReturn(Optional.of(entity));
        Mockito.when(serviceTransaction.cardTransaction(Mockito.any(RegisterCard.class))).thenReturn(registerCard);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerCard).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        Assertions.assertThat(result).isNotNull();

    }
}
