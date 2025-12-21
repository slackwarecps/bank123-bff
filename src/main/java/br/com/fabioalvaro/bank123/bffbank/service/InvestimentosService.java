package br.com.fabioalvaro.bank123.bffbank.service;

import br.com.fabioalvaro.bank123.bffbank.dto.CarteiraResponse;
import br.com.fabioalvaro.bank123.bffbank.dto.CarteiraResumoResponse;
import br.com.fabioalvaro.bank123.bffbank.dto.InvestimentoItemResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InvestimentosService {

    public CarteiraResponse obterCarteira(Integer accountId) {
        // Mock data as requested
        List<InvestimentoItemResponse> investimentos = List.of(
            InvestimentoItemResponse.builder().ticker("BBAS3").nomeEmpresa("BCO BRASIL S.A.").tipo("ON").instituicao("NU INVEST CORRETORA DE VALORES").quantidade(1057).precoFechamento(new BigDecimal("21.44")).valorTotal(new BigDecimal("22662.08")).build(),
            InvestimentoItemResponse.builder().ticker("BBSE3").nomeEmpresa("BB SEGURIDADE PARTICIPAÇÕES S.A.").tipo("ON").instituicao("NU INVEST CORRETORA DE VALORES").quantidade(407).precoFechamento(new BigDecimal("35.27")).valorTotal(new BigDecimal("14354.89")).build(),
            InvestimentoItemResponse.builder().ticker("CMIG4").nomeEmpresa("CIA ENERGETICA DE MINAS GERAIS - CEMIG").tipo("PN").instituicao("NU INVEST CORRETORA DE VALORES").quantidade(901).precoFechamento(new BigDecimal("11.08")).valorTotal(new BigDecimal("9983.08")).build(),
            InvestimentoItemResponse.builder().ticker("CSUD3").nomeEmpresa("CSU DIGITAL S.A.").tipo("ON").instituicao("NU INVEST CORRETORA DE VALORES").quantidade(1).precoFechamento(new BigDecimal("19.70")).valorTotal(new BigDecimal("19.70")).build(),
            InvestimentoItemResponse.builder().ticker("ITUB3").nomeEmpresa("ITAU UNIBANCO HOLDING S.A.").tipo("ON").instituicao("NU INVEST CORRETORA DE VALORES").quantidade(1).precoFechamento(new BigDecimal("36.56")).valorTotal(new BigDecimal("36.56")).build(),
            InvestimentoItemResponse.builder().ticker("PETR4").nomeEmpresa("PETROLEO BRASILEIRO S.A. PETROBRAS").tipo("PN").instituicao("NU INVEST CORRETORA DE VALORES").quantidade(400).precoFechamento(new BigDecimal("31.01")).valorTotal(new BigDecimal("12404.00")).build(),
            InvestimentoItemResponse.builder().ticker("TASA4").nomeEmpresa("TAURUS ARMAS S.A.").tipo("PN").instituicao("NU INVEST CORRETORA DE VALORES").quantidade(3235).precoFechamento(new BigDecimal("4.66")).valorTotal(new BigDecimal("15075.10")).build(),
            InvestimentoItemResponse.builder().ticker("VALE3").nomeEmpresa("VALE S.A.").tipo("ON").instituicao("NU INVEST CORRETORA DE VALORES").quantidade(234).precoFechamento(new BigDecimal("70.85")).valorTotal(new BigDecimal("16578.90")).build()
        );

        CarteiraResumoResponse resumo = CarteiraResumoResponse.builder()
                .totalInvestido(new BigDecimal("91114.31"))
                .quantidadeAtivos(8)
                .build();

        return CarteiraResponse.builder()
                .carteira(investimentos)
                .resumo(resumo)
                .build();
    }
}
