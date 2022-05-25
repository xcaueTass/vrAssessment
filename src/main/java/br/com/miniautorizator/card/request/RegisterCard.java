package br.com.miniautorizator.card.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@JsonInclude(NON_NULL)
public class RegisterCard {

    @JsonProperty("card_number")
    private String cardNumber;
    private String password;
    @JsonProperty("value_card")
    private Double valueCard;


}
