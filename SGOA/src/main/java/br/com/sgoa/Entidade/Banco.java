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

/**
 *
 * @author orin
 */
@Entity
@Table(name = "Banco")
public class Banco implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private long idBanco;

    @Column(name = "banco_nome", nullable = false)
    @Getter @Setter private String nomeBanco;

    @Override
    public Long getId() {
       return idBanco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Banco)) return false;

        Banco that = (Banco) o;

        return idBanco == that.idBanco;
    }

    @Override
    public int hashCode() {
        return (int) (idBanco ^ (idBanco >>> 32));
    }

    @Override
    public String toString() {
        return "Banco{" +
                "idBanco=" + idBanco +
                ", nomeBanco='" + nomeBanco + '\'' +
                '}';
    }
}
