package br.com.fabioalvaro.bank123.bffbank.service;

import br.com.fabioalvaro.bank123.bffbank.dto.SaldoResponse;
import br.com.fabioalvaro.bank123.bffbank.dto.TransacaoResponse;
import br.com.fabioalvaro.bank123.bffbank.model.Conta;
import br.com.fabioalvaro.bank123.bffbank.repository.ContaRepository;
import br.com.fabioalvaro.bank123.bffbank.repository.LivroCaixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtratoService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private LivroCaixaRepository livroCaixaRepository;

    public SaldoResponse buscarSaldo(Integer numeroConta) {
        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta " + numeroConta + " não encontrada"));
        
        return new SaldoResponse(conta.getNumeroConta(), conta.getSaldo());
    }

    public List<TransacaoResponse> buscarExtrato(Integer numeroConta) {
        // Valida se a conta existe antes de buscar o extrato
        if (!contaRepository.existsById(numeroConta)) {
             throw new RuntimeException("Conta " + numeroConta + " não encontrada");
        }

        return livroCaixaRepository.findByNumeroConta(numeroConta).stream()
                .map(t -> TransacaoResponse.builder()
                        .idTransacao(t.getIdTransacao())
                        .dataTransacao(t.getDataTransacao())
                        .valorTransacao(t.getValorTransacao())
                        .operacao(t.getOperacao())
                        .destino(t.getDestino())
                        .origem(t.getOrigem())
                        .build())
                .collect(Collectors.toList());
    }
}