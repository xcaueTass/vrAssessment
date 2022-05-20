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

    @Autowired
    CardRepository cardRepository;

    @SneakyThrows
    public RegisterCard cardRegister(RegisterCard registerCard) {

        log.info("[INICIO] - ServiceRegister - Validando se existe cartão");
        validRegister(registerCard);

        log.info("ServiceRegister - Cartão salvo com sucesso!");
        return registerCard;
    }

    @SneakyThrows
    public RegisterCard cardBalance(String numeroCartao) {

        log.info("[INICIO] - ServiceRegister - Buscando informações na base de dados com nome do cartao: {}",
                numeroCartao);
        var dataCard = cardRepository.findCard(numeroCartao)
                .orElseThrow(() -> new DataBaseExceptions("CARTAO_INEXISTENTE"));

        log.info("ServiceRegister - Retornando saldo do cartão");
        return RegisterCard.builder().cardNumber(dataCard.getCardNumber()).valueCard(dataCard.getValueCard()).build();

    }

    @SneakyThrows
    public RegisterCard cardTransaction(RegisterCard registerCard) {

        log.info("[INICIO] - ServiceRegister - Validando se existe cartão");
        Optional<CardEntity> entity = cardRepository.findCardAndPassword(registerCard.getCardNumber(),
                registerCard.getPassword());
        registerCard.isCardExist(entity);

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

    @SneakyThrows
    private void validRegister(RegisterCard registerCard) {
        log.info("ServiceRegister - Buscando informações no banco de dados");
        Optional<CardEntity> entity = cardRepository.findCard(registerCard.getCardNumber());

        log.info("ServiceRegister - validando se existe cartão");
        registerCard.isCardEmpty(entity);

        log.info("ServiceRegister - Salvando novo cartão na base de dados!");
        var cardEntity = CardEntity.builder().cardNumber(registerCard.getCardNumber())
                .password(registerCard.getPassword()).valueCard(500.00).build();

        cardRepository.save(cardEntity);
    }

}
