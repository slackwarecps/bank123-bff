package br.com.fabioalvaro.bank123.bffbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilResponse {
    private String nomeCompleto;
    private String endereco;
    private String agencia;
    private Integer numeroConta;
}
