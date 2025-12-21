package br.com.fabioalvaro.bank123.bffbank.controller;

import br.com.fabioalvaro.bank123.bffbank.dto.TransferenciaContaRequest;
import br.com.fabioalvaro.bank123.bffbank.dto.TransferenciaContaResponse;
import br.com.fabioalvaro.bank123.bffbank.service.MovimentacoesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bff-bank123/movimentacoes/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "movimentacoes-controller", description = "API de Movimentações")
public class MovimentacoesController {

    private final MovimentacoesService movimentacoesService;

    @Operation(summary = "Realizar Transferência entre Contas", operationId = "transferenciaConta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transferência criada com sucesso",
                    content = @Content(schema = @Schema(implementation = TransferenciaContaResponse.class)))
    })
    @PostMapping("/transferencia-conta")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('cliente_pf') and hasAuthority('write:transacoes')")
    public ResponseEntity<TransferenciaContaResponse> transferenciaConta(
            @Parameter(description = "Token de autorização JWT", required = false)
            @RequestHeader(value = "Authorization", required = false) String authorization,

            @Parameter(description = "ID da conta do usuário", required = true)
            @RequestHeader(value = "x-account-id") Integer accountId,

            @Parameter(description = "ID de correlação para rastreamento de requisições", required = false)
            @RequestHeader(value = "x-correlation-id", required = false, defaultValue = "uuid-fake") String correlationId,

            @RequestBody TransferenciaContaRequest request
    ) {
        log.info("Processando transferência para accountId: {}, valor: {}, correlationId: {}", 
                accountId, request.getValor(), correlationId);
        
        TransferenciaContaResponse response = movimentacoesService.realizarTransferencia(accountId, correlationId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
