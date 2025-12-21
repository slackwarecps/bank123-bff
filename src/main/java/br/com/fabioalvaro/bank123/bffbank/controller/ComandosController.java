package br.com.fabioalvaro.bank123.bffbank.controller;

import br.com.fabioalvaro.bank123.bffbank.dto.MudarClaimRequest;
import br.com.fabioalvaro.bank123.bffbank.service.OnboardingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comandos/v1")
@Tag(name = "comandos-controller", description = "Endpoints para execução de comandos administrativos e operacionais")
@Slf4j
public class ComandosController {

    @Autowired
    private OnboardingService onboardingService;

    @Operation(summary = "Adicionar/Refazer Claims", description = "Reaplica as custom claims do Firebase para uma conta específica via número da conta, forçando um UID específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Claims atualizadas com sucesso."),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar claims.")
    })
    @PostMapping("/funcao-mudar-claim/{numeroconta}")
    public ResponseEntity<String> adicionarClaimsAoUsuario(
            @Parameter(description = "Número da conta para atualizar as claims", required = true)
            @PathVariable("numeroconta") Integer numeroConta,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Payload contendo o UID do usuário (opcional, se fornecido será usado)", required = true)
            @RequestBody MudarClaimRequest request) {
        
        log.info("Recebida requisição para atualizar claims da conta: {} com UID: {}", numeroConta, request.getUid());
        try {
            if (request.getUid() != null && !request.getUid().isEmpty()) {
                onboardingService.refazerClaims(numeroConta, request.getUid());
            } else {
                onboardingService.refazerClaims(numeroConta);
            }
            return ResponseEntity.ok("Claims atualizadas com sucesso para a conta " + numeroConta);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar claims: {}", e.getMessage());
            if (e.getMessage().contains("Conta não encontrada")) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }
}
