package br.com.fabioalvaro.bank123.bffbank.service;

import br.com.fabioalvaro.bank123.bffbank.dto.WebhookFirebasePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OnboardingService {
    
    public void processarWebhook(WebhookFirebasePayload payload) {
        log.info("Recebido webhook de onboarding: {}", payload);
        // Lógica de negócio futura
    }
}
