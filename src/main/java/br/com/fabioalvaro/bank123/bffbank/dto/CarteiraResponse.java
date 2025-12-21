package br.com.fabioalvaro.bank123.bffbank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarteiraResponse {
    private List<InvestimentoItemResponse> carteira;
    private CarteiraResumoResponse resumo;
}
