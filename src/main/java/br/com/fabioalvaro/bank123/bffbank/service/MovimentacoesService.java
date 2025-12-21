package br.com.fabioalvaro.bank123.bffbank.service;

import br.com.fabioalvaro.bank123.bffbank.dto.TransferenciaContaRequest;
import br.com.fabioalvaro.bank123.bffbank.dto.TransferenciaContaResponse;
import br.com.fabioalvaro.bank123.bffbank.model.LivroCaixa;
import br.com.fabioalvaro.bank123.bffbank.repository.LivroCaixaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovimentacoesService {

    private final LivroCaixaRepository livroCaixaRepository;

    public TransferenciaContaResponse realizarTransferencia(Integer accountId, String correlationId, TransferenciaContaRequest request) {
        log.info("Iniciando serviço de transferência. AccountId: {}, CorrelationId: {}", accountId, correlationId);

        LivroCaixa livroCaixa = new LivroCaixa();
        livroCaixa.setDataTransacao(LocalDateTime.now());
        livroCaixa.setValorTransacao(new BigDecimal(request.getValor()));
        livroCaixa.setNumeroConta(accountId);
        livroCaixa.setOperacao("SAIDA");
        livroCaixa.setDestino(request.getDestinoConta());
        livroCaixa.setOrigem(String.valueOf(accountId));

                LivroCaixa savedLivroCaixa = livroCaixaRepository.save(livroCaixa);

                log.info("Transação salva no livro caixa com sucesso. ID: {}", savedLivroCaixa.getIdTransacao());

        

                return TransferenciaContaResponse.builder()

                        .valor(request.getValor())

                        .transacaoId(String.valueOf(savedLivroCaixa.getIdTransacao()))

                        .build();
    }
}
