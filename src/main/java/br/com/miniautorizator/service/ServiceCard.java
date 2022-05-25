package br.com.miniautorizator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.miniautorizator.card.entities.CardEntity;
import br.com.miniautorizator.card.exceptions.CardException;
import br.com.miniautorizator.card.exceptions.DataBaseExceptions;
import br.com.miniautorizator.card.repositories.CardRepository;
import br.com.miniautorizator.card.request.RegisterCard;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServiceCard {

    private static final String SALVO = "Cartão salvo com sucesso";
    private static final String CADASTRADO = "Cartão ja cadastrado";
    private static final String BODYNULL = "Número do cartao ou Senha não pode ser nulo";
    @Autowired
    CardRepository cardRepository;

    @SneakyThrows
    public String cardRegister(RegisterCard registerCard) {

        log.info("[INICIO] - ServiceRegister - Validando se body nulo");
        requestIsNull(registerCard);

        log.info("ServiceRegister - Validando se existe registro");
        var response = validRegister(registerCard);

        log.info("ServiceRegister - Cartão salvo com sucesso!");
        return response;
    }

    public RegisterCard cardBalance(String numeroCartao) throws DataBaseExceptions {

        log.info("[INICIO] - ServiceRegister - Buscando informações na base de dados com nome do cartao: {}",
                numeroCartao);

        var dataCard = cardRepository.findCard(numeroCartao)
                .orElseThrow(() -> new DataBaseExceptions("CARTAO_INEXISTENTE"));

        log.info("ServiceRegister - Retornando saldo do cartão");
        return RegisterCard.builder().cardNumber(dataCard.getCardNumber()).valueCard(dataCard.getValueCard()).build();

    }

    private String validRegister(RegisterCard registerCard) {
        log.info("ServiceRegister - Buscando informações no banco de dados");
        Optional<CardEntity> entity = cardRepository.findCard(registerCard.getCardNumber());

        if (entity.isEmpty()) {
            saveCard(registerCard);
            return SALVO;
        }
        return CADASTRADO;
    }

    @SneakyThrows
    private void requestIsNull(RegisterCard registerCard) {
        if (registerCard.getCardNumber().isEmpty() || registerCard.getPassword().isEmpty()) {
            throw new CardException(BODYNULL);
        }
    }

    private void saveCard(RegisterCard registerCard) {
        log.info("ServiceRegister - Salvando novo cartão na base de dados!");
        var cardEntity = CardEntity.builder().cardNumber(registerCard.getCardNumber())
                .password(registerCard.getPassword()).valueCard(500.00).build();

        cardRepository.save(cardEntity);
    }

}
