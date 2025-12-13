package br.com.fabioalvaro.bank123.bffbank.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "contas")
public class Conta {

    @Id
    @Column(name = "numeroconta")
    private Integer numeroConta;


    @Column(name = "datacriacao")
    private LocalDateTime dataCriacao;

    @Column(name = "saldo")
    private BigDecimal saldo;
}