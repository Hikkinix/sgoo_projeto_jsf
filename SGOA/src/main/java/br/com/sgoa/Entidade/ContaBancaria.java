/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Entidade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author orin
 */
@Entity
@Table(name = "contaBancaria")
public class ContaBancaria implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private Long idContaBancaria;

    @JoinColumn(name = "conta_banco", nullable = false)
    @ManyToOne
    @Getter @Setter private Banco banco;

    @Column(name = "conta_caixa", nullable = false)
    @Getter @Setter private Boolean caixaInterno = false;

    @Column(name = "conta_numero", nullable = false)
    @Getter @Setter private String numeroConta = "";

    @Column(name = "conta_agencia", nullable = false)
    @Getter @Setter private String agenciaConta = "";

    @Column(name = "conta_saldo_debito", nullable = false)
    @Getter @Setter private BigDecimal saldoDebitoConta = BigDecimal.ZERO;

    @Column(name = "conta_saldo_credito", nullable = false)
    @Getter @Setter private BigDecimal saldoCreditoConta = BigDecimal.ZERO;

    @Column(name = "conta_saldo", nullable = false)
    private BigDecimal saldoConta = BigDecimal.ZERO;

    @Override
    public Long getId() {
       return idContaBancaria;
    }

    public String getDescricaoConta(){
        return banco.getNomeBanco()+", "+ numeroConta+"-"+agenciaConta;
    }

    public BigDecimal getSaldoConta() {
        return saldoConta = saldoCreditoConta.subtract(saldoDebitoConta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContaBancaria)) return false;

        ContaBancaria that = (ContaBancaria) o;

        return idContaBancaria == that.idContaBancaria;
    }

    @Override
    public int hashCode() {
        return (int) (idContaBancaria ^ (idContaBancaria >>> 32));
    }

    @Override
    public String toString() {
        return  idContaBancaria.toString();
    }
}
