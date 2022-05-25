package br.com.miniautorizator.service;

import br.com.miniautorizator.card.entities.CardEntity;
import br.com.miniautorizator.card.exceptions.CardException;
import br.com.miniautorizator.card.repositories.CardRepository;
import br.com.miniautorizator.card.request.RegisterCard;
import br.com.miniautorizator.card.response.ResponseCard;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ServiceTransaction {
    @Autowired
    CardRepository cardRepository;


    @SneakyThrows
    public RegisterCard cardTransaction(RegisterCard registerCard) {
        ResponseCard responseCard = new ResponseCard();
        log.info("[INICIO] - ServiceRegister - Validando se existe cartão");
        Optional<CardEntity> entity = cardRepository.findCardAndPassword(registerCard.getCardNumber(),
                registerCard.getPassword());
        responseCard.isCardExist(entity);

        log.info("ServiceRegister - Validando se existe saldo no cartão");
        validBalance(registerCard, entity);

        log.info("ServiceRegister - Retornando resposta cartão");
        return registerCard;

    }

    @SneakyThrows
    private void validBalance(RegisterCard registerCard, Optional<CardEntity> entity) {

        if (entity.get().getValueCard() >= registerCard.getValueCard()) {

            log.info("ServiceRegister - Saldo cartão: {}, Saldo a ser descontado: {}", entity.get().getValueCard(), registerCard.getValueCard());
            var currentBalance = entity.get().getValueCard() - registerCard.getValueCard();

            log.info("ServiceRegister - Saldo atual: {}", currentBalance);
            updateBalance(registerCard, entity, currentBalance);

        } else {
            throw new CardException("SALDO_INSUFICIENTE");
        }
    }

    private void updateBalance(RegisterCard registerCard, Optional<CardEntity> entity, double currentBalance) {
        var cardEntity = CardEntity.builder().id(entity.get().getId()).cardNumber(registerCard.getCardNumber())
                .password(registerCard.getPassword()).valueCard(currentBalance).build();

        cardRepository.save(cardEntity);
    }
}
