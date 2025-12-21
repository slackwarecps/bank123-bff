package br.com.fabioalvaro.bank123.bffbank.service;

import br.com.fabioalvaro.bank123.bffbank.dto.WebhookFirebasePayload;
import br.com.fabioalvaro.bank123.bffbank.model.Conta;
import br.com.fabioalvaro.bank123.bffbank.repository.ContaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
public class OnboardingService {

    @Autowired
    private ContaRepository contaRepository;
    
    public void processarWebhook(WebhookFirebasePayload payload) {
        log.info("Recebido webhook de onboarding: {}", payload);
        
        try {
            Conta novaConta = new Conta();
            novaConta.setEmail(payload.getEmail());
            novaConta.setIdUserFirebase(payload.getUid());
            novaConta.setDataCriacao(LocalDateTime.now());
            novaConta.setSaldo(BigDecimal.ZERO);
            novaConta.setStatus("ativa");

            Conta contaSalva = contaRepository.save(novaConta);
            log.info("Nova conta criada com sucesso: {}", contaSalva);
        } catch (Exception e) {
            log.error("Erro ao processar webhook de onboarding: ", e);
            throw new RuntimeException("Erro ao criar conta", e);
        }
    }
}
