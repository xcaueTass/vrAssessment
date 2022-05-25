package br.com.miniautorizator.card.response;

import br.com.miniautorizator.card.entities.CardEntity;
import br.com.miniautorizator.card.exceptions.CardException;
import lombok.SneakyThrows;

import java.util.Optional;

public class ResponseCard {
    @SneakyThrows
    public void isCardExist(Optional<CardEntity> usersEntity) {
        if (usersEntity.isEmpty()) {
            throw new CardException("CARTAO_INEXISTENTE / SENHA_INVALIDA");
        }
    }
}
