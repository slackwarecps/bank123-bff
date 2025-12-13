package br.com.fabioalvaro.bank123.bffbank.controller;

import br.com.fabioalvaro.bank123.bffbank.dto.SaldoResponse;
import br.com.fabioalvaro.bank123.bffbank.dto.TransacaoResponse;
import br.com.fabioalvaro.bank123.bffbank.service.ExtratoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExtratoController.class)
public class ExtratoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExtratoService extratoService;

    @Test
    @WithMockUser
    public void deveRetornarSaldo_quandoExistirConta() throws Exception {
        // Arrange
        Integer numeroConta = 123456;
        BigDecimal saldo = new BigDecimal("1500.75");
        SaldoResponse saldoResponse = new SaldoResponse(numeroConta, saldo);

        when(extratoService.buscarSaldo(numeroConta)).thenReturn(saldoResponse);

                mockMvc.perform(get("/bff-bank123/extrato/v1/saldo")
                                .header("x-account-id", numeroConta)
                                .header("x-correlation-id", "test-id")                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroConta").value(numeroConta))
                .andExpect(jsonPath("$.saldo").value(saldo.doubleValue()));
    }

    @Test
    @WithMockUser
    public void deveRetornarExtrato_quandoExistirConta() throws Exception {
        // Arrange
        Integer numeroConta = 123456;
        List<TransacaoResponse> extrato = new ArrayList<>();
        extrato.add(TransacaoResponse.builder()
                .idTransacao(1)
                .dataTransacao(LocalDateTime.now())
.valorTransacao(new BigDecimal("100.00"))
                .operacao("ENTRADA")
                .build());

        when(extratoService.buscarExtrato(numeroConta)).thenReturn(extrato);

        // Act & Assert
        mockMvc.perform(get("/bff-bank123/extrato/v1/listagem")
                        .header("x-account-id", numeroConta)
                        .header("x-correlation-id", "test-id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].idTransacao").value(1));
    }
}
