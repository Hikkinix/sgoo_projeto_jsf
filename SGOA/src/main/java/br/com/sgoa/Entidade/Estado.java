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

@Entity
@Table(name = "estado")
public class Estado implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private Long idEstado;

    @Column(name = "nome_estado", length = 100, nullable = false)
    @Getter @Setter private String nomeEstado;

    @Column(name = "uf_estado", length = 3)
    @Getter @Setter private String uf;

    @Override
    public Long getId() {
        return idEstado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estado)) return false;

        Estado estado = (Estado) o;

        return idEstado != null ? idEstado.equals(estado.idEstado) : estado.idEstado == null;
    }

    @Override
    public int hashCode() {
        return idEstado != null ? idEstado.hashCode() : 0;
    }

    @Override
    public String toString() {
        return idEstado.toString();
    }
}
