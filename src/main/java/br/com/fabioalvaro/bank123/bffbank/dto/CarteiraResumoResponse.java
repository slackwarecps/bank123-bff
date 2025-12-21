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
public class CarteiraResumoResponse {
    private BigDecimal totalInvestido;
    private Integer quantidadeAtivos;
}
