package br.com.miniautorizator.controller;

import br.com.miniautorizator.card.request.RegisterCard;
import br.com.miniautorizator.service.ServiceTransaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transacao")
@Slf4j
public class TransactionController {

    @Autowired
    ServiceTransaction serviceTransaction;

    @Operation(summary = "Cria um novo cartao")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity"),
    })
    @PostMapping
    public ResponseEntity<RegisterCard> transactions(@Validated @RequestBody RegisterCard registerCard) {

        log.info("[INICIO] - CardController - Efetuando uma transação");
        serviceTransaction.cardTransaction(registerCard);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
