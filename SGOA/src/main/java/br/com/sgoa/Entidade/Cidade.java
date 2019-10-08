package br.com.sgoa.Entidade;


import javax.persistence.*;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "cidade")
public class Cidade implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long idCidade;

    @Column(name = "nome_cidade", nullable = false)
    @Getter
    @Setter
    private String nomeCidade;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "estado_cid")
    @Getter
    @Setter
    private Estado estado;

    @Override
    public Long getId() {
        return idCidade;
    }

    public String getCidadeEstado() {
        return this.nomeCidade + "/" + this.estado.getNomeEstado();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cidade)) return false;

        Cidade cidade = (Cidade) o;

        if (idCidade != cidade.idCidade) return false;
        return estado != null ? estado.equals(cidade.estado) : cidade.estado == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (idCidade ^ (idCidade >>> 32));
        result = 31 * result + (nomeCidade != null ? nomeCidade.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return idCidade.toString();
    }
}
