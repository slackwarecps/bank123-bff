package br.com.fabioalvaro.bank123.bffbank.controller;

import br.com.fabioalvaro.bank123.bffbank.dto.WebhookFirebasePayload;
import br.com.fabioalvaro.bank123.bffbank.service.OnboardingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/onboarding/v1")
@Tag(name = "onboarding-controller", description = "Gerencia operações de onboarding")
public class OnboardingController {

    @Autowired
    private OnboardingService onboardingService;

    @Operation(summary = "Recebe webhook do Firebase", description = "Processa dados de criação de usuário via webhook.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Webhook recebido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos.")
    })
    @PostMapping("/webhook-firebase-add")
    public ResponseEntity<WebhookFirebasePayload> receberWebhook(
            @RequestBody WebhookFirebasePayload payload,
            @RequestHeader Map<String, String> headers) {
        log.info(">>> REQUEST [POST] /onboarding/v1/webhook-firebase-add");
        log.info(">>> REQUEST HEADERS: {}", headers);
        log.info(">>> REQUEST BODY: {}", payload);

        onboardingService.processarWebhook(payload);

        ResponseEntity<WebhookFirebasePayload> response = ResponseEntity.ok(payload);

        log.info("<<< RESPONSE [POST] /onboarding/v1/webhook-firebase-add");
        log.info("<<< RESPONSE STATUS CODE: {}", response.getStatusCode());
        log.info("<<< RESPONSE HEADERS: {}", response.getHeaders());
        log.info("<<< RESPONSE BODY: {}", response.getBody());

        return response;
    }
}
