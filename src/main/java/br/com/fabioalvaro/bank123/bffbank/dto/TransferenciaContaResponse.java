package br.com.fabioalvaro.bank123.bffbank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaContaResponse {
    private String valor;

    @JsonProperty("transacao-id")
    private String transacaoId;
}
