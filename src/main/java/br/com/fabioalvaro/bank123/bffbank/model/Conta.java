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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contas_numeroconta_seq")
    @SequenceGenerator(name = "contas_numeroconta_seq", sequenceName = "contas_numeroconta_seq", allocationSize = 1)
    @Column(name = "numeroconta")
    private Integer numeroConta;

    @Column(name = "datacriacao")
    private LocalDateTime dataCriacao;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "email")
    private String email;

    @Column(name = "id_user_firebase")
    private String idUserFirebase;

    @Column(name = "status")
    private String status;
}