package br.com.fabioalvaro.bank123.bffbank.controller;

import br.com.fabioalvaro.bank123.bffbank.dto.SaldoResponse;
import br.com.fabioalvaro.bank123.bffbank.dto.TransacaoResponse;
import br.com.fabioalvaro.bank123.bffbank.service.ExtratoService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bff-bank123/extrato/v1")
public class ExtratoController {

    @Autowired
    private ExtratoService service;

    @GetMapping("/saldo")
    public ResponseEntity<SaldoResponse> consultarSaldo(
            @RequestHeader(value = "Authorization", required = false) String token, // Opcional por enquanto
            @RequestHeader("x-account-id") Integer accountId,
            @RequestHeader(value = "x-correlationId", defaultValue = "uuid-fake") String correlationId) {
        
        // Log para rastreamento
        MDC.put("correlationId", correlationId);
        System.out.println("Consultando saldo para conta: " + accountId);

        try {
            // Usa o header x-account-id para buscar no banco
            return ResponseEntity.ok(service.buscarSaldo(accountId));
        } finally {
            MDC.clear();
        }
    }

    @GetMapping("/listagem")
    public ResponseEntity<List<TransacaoResponse>> listarExtrato(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestHeader("x-account-id") Integer accountId,
            @RequestHeader(value = "x-correlationId", defaultValue = "uuid-fake") String correlationId) {
        
        MDC.put("correlationId", correlationId);
        System.out.println("Consultando extrato para conta: " + accountId);
        
        try {
            return ResponseEntity.ok(service.buscarExtrato(accountId));
        } finally {
            MDC.clear();
        }
    }
}