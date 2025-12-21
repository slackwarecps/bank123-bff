package br.com.fabioalvaro.bank123.bffbank.controller;

import br.com.fabioalvaro.bank123.bffbank.dto.CarteiraResponse;
import br.com.fabioalvaro.bank123.bffbank.service.InvestimentosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff-bank123/investimentos/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "investimentos-controller", description = "API de Investimentos")
public class InvestimentosController {

    private final InvestimentosService investimentosService;

    @Operation(summary = "Consultar Carteira de Investimentos", operationId = "consultarCarteira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CarteiraResponse.class)))
    })
    @GetMapping("/carteira")
    @PreAuthorize("hasAuthority('cliente_pf') and hasAuthority('read:investimentos')")
    public ResponseEntity<CarteiraResponse> consultarCarteira(
            @Parameter(description = "Token de autorização JWT", required = false)
            @RequestHeader(value = "Authorization", required = false) String authorization,

            @Parameter(description = "ID da conta do usuário", required = true)
            @RequestHeader(value = "x-account-id") Integer accountId,

            @Parameter(description = "ID de correlação para rastreamento de requisições", required = false)
            @RequestHeader(value = "x-correlation-id", required = false, defaultValue = "uuid-fake") String correlationId
    ) {
        log.info("Consultando carteira para accountId: {}, correlationId: {}", accountId, correlationId);
        CarteiraResponse response = investimentosService.obterCarteira(accountId);
        return ResponseEntity.ok(response);
    }
}
