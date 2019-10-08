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
@Table(name = "Material")
public class Material implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private long idMaterial;

    @Column(name = "material_nome")
    @Getter @Setter private double nomeMaterial;

    @Override
    public Long getId() {
        return idMaterial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material)) return false;

        Material material = (Material) o;

        return idMaterial == material.idMaterial;
    }

    @Override
    public int hashCode() {
        return (int) (idMaterial ^ (idMaterial >>> 32));
    }

    @Override
    public String toString() {
        return "Material{" +
                "idMaterial=" + idMaterial +
                ", nomeMaterial=" + nomeMaterial +
                '}';
    }


}
