package br.com.miniautorizator.card.request;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.miniautorizator.card.entities.CardEntity;
import br.com.miniautorizator.card.exceptions.CardException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class RegisterCard {

	@JsonProperty("card_number")
	private String cardNumber;
	private String password;
	@JsonProperty("value_card")
	private Double valueCard;

	@SneakyThrows
	public void isCardEmpty(Optional<CardEntity> usersEntity) {
		if (!usersEntity.isEmpty()) {
			throw new CardException("Ja existe cartao cadastrado");
		}

	}

	@SneakyThrows
	public void isCardExist(Optional<CardEntity> usersEntity) {
		if (usersEntity.isEmpty()) {
			throw new CardException("CARTAO_INEXISTENTE / SENHA_INVALIDA");
		}

	}
}
