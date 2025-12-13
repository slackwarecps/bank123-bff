package br.com.fabioalvaro.bank123.bffbank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoResponse {
    private Integer idTransacao;
    private LocalDateTime dataTransacao;
    private BigDecimal valorTransacao;
    private String operacao;
    private String destino;
    private String origem;
}