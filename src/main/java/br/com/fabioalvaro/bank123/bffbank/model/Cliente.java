package br.com.fabioalvaro.bank123.bffbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Column;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "email")
    private String email;

    @Column(name = "numeroconta")
    private Integer numeroConta;



    @Column(name = "endereco_logradouro")
    private String enderecoLogradouro;

    @Column(name = "endereco_numero")
    private String enderecoNumero;

    @Column(name = "endereco_complemento")
    private String enderecoComplemento;

    @Column(name = "endereco_bairro")
    private String enderecoBairro;

    @Column(name = "endereco_cidade")
    private String enderecoCidade;

    @Column(name = "endereco_estado")
    private String enderecoEstado;

    @Column(name = "endereco_cep")
    private String enderecoCep;
}
