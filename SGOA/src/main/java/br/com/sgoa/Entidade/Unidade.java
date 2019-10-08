/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Entidade;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author orin
 */
@Entity
@Table(name = "unidade")
public class Unidade implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private Long idUnidade;

    @Column(name = "un_casas_decimais")
    @Getter @Setter
    private Integer casasDecimais = 0;

    @Column(name = "un_nome", nullable = false)
    @Getter @Setter
    private String nomeUnidade;

    @Column(name = "un_abreviacao", length = 5)
    @Getter @Setter
    private String abreviacao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unidade)) return false;

        Unidade unidade = (Unidade) o;

        return idUnidade.equals(unidade.idUnidade);
    }

    @Override
    public int hashCode() {
        return (int) (idUnidade ^ (idUnidade >>> 32));
    }

    @Override
    public String toString() {
        return idUnidade.toString();
    }

    @Override
    public Long getId() {
        return idUnidade;
    }
}
