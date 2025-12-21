package br.com.fabioalvaro.bank123.bffbank.service;

import br.com.fabioalvaro.bank123.bffbank.dto.WebhookFirebasePayload;
import br.com.fabioalvaro.bank123.bffbank.model.Conta;
import br.com.fabioalvaro.bank123.bffbank.repository.ContaRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class OnboardingService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private FirebaseAuth firebaseAuth;
    
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

            // Adiciona Custom Claims ao usuário no Firebase
            adicionarClaimsAoUsuario(contaSalva);

        } catch (Exception e) {
            log.error("Erro ao processar webhook de onboarding: ", e);
            throw new RuntimeException("Erro ao criar conta", e);
        }
    }

    private void adicionarClaimsAoUsuario(Conta conta) {
        try {
            Map<String, Object> appClaims = new HashMap<>();
            appClaims.put("numeroconta", conta.getNumeroConta());
            appClaims.put("default_role", "cliente_pf");
            appClaims.put("allowed_roles", java.util.List.of("cliente_pf"));
            appClaims.put("scope", "read:saldo read:extrato write:transacoes read:perfil");

            Map<String, Object> claims = new HashMap<>();
            claims.put("bank123/jwt/claims", appClaims);

            firebaseAuth.setCustomUserClaims(conta.getIdUserFirebase(), claims);
            log.info("Custom claims adicionadas ao usuário {}: {}", conta.getIdUserFirebase(), claims);
        } catch (FirebaseAuthException e) {
            log.error("Erro ao adicionar custom claims ao usuário {}: ", conta.getIdUserFirebase(), e);
            // Não lançamos exceção aqui para não desfazer a criação da conta, 
            // mas poderia ser implementado um mecanismo de retry ou fila DLQ.
        }
    }
}
