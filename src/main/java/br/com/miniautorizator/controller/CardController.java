package br.com.miniautorizator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import br.com.miniautorizator.card.request.RegisterCard;
import br.com.miniautorizator.service.ServiceCard;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/cartoes")
@Slf4j
public class CardController {

    @Autowired
    ServiceCard serviceCard;

    @Operation(summary = "Cria um novo cartao")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity"),
    })
    @PostMapping
    public ResponseEntity<RegisterCard> register(@Validated @RequestBody RegisterCard registerCard) {

        log.info("[INICIO] - CardController - Registrando novo cartão");
        var response = serviceCard.cardRegister(registerCard);

        if (response.equalsIgnoreCase("Cartão salvo com sucesso")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(registerCard);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(registerCard);
        }
    }

    @Operation(summary = "Cria um novo cartao")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity"),
    })
    @PostMapping("/transacao")
    public ResponseEntity<RegisterCard> transactions(@Validated @RequestBody RegisterCard registerCard) {

        log.info("[INICIO] - CardController - Efetuando uma transação");
        serviceCard.cardTransaction(registerCard);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Cria um novo cartao")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    @GetMapping(path = {"/{numeroCartao}"})
    public ResponseEntity<RegisterCard> balanceCard(@PathVariable String numeroCartao) {

        log.info("[INICIO] - Consultando cartao: {}", numeroCartao);
        return ResponseEntity.status(HttpStatus.OK).body(serviceCard.cardBalance(numeroCartao));
    }
}
