/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Entidade;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author orin
 */
@Entity
@Table(name = "Marca")
public class Marca implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private long idMarca;

    @Column(name = "marca_nome", nullable = false)
    @Getter @Setter private String nomeMarca;

    @Override
    public Long getId() {
       return idMarca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Marca)) return false;

        Marca that = (Marca) o;

        return idMarca == that.idMarca;
    }

    @Override
    public int hashCode() {
        return (int) (idMarca ^ (idMarca >>> 32));
    }

    @Override
    public String toString() {
        return "Marca{" +
                "idMarca=" + idMarca +
                ", nomeMarca='" + nomeMarca + '\'' +
                '}';
    }
}
