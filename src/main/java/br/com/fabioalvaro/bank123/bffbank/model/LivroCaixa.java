package br.com.fabioalvaro.bank123.bffbank.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "livrocaixa")
public class LivroCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtransacao")
    private Integer idTransacao;

    @Column(name = "datatransacao")
    private LocalDateTime dataTransacao;

    @Column(name = "valortransacao")
    private BigDecimal valorTransacao;

    @Column(name = "numeroconta")
    private Integer numeroConta; // Relacionamento lógico

    @Column(name = "operacao")
    private String operacao; // ENTRADA ou SAIDA

    @Column(name = "destino")
    private String destino;

    @Column(name = "origem")
    private String origem;
}