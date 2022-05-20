package br.com.miniautorizator.card.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class CardException extends Exception {

    private static final long serialVersionUID = 1L;
    public CardException(String message) {

        super(message);

    }

}
