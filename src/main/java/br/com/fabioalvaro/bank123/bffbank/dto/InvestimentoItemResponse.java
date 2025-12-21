package br.com.fabioalvaro.bank123.bffbank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvestimentoItemResponse {
    private String ticker;
    private String nomeEmpresa;
    private String tipo;
    private String instituicao;
    private Integer quantidade;
    private BigDecimal precoFechamento;
    private BigDecimal valorTotal;
}
