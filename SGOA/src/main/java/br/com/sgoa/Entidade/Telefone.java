package br.com.sgoa.Entidade;

import br.com.sgoa.Enums.TipoTelefone;
import javax.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Telefone implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private Long idTelefone;

    @Column(name = "numero_telefone")
    @Getter @Setter private String numero;

    @Column(name = "tipo_telefone")
    @Enumerated(EnumType.STRING)
    @Getter @Setter private TipoTelefone tipo = TipoTelefone.CELULAR;

    @Column(name = "obs_telefone")
    @Getter @Setter private String observacao;

    @Column(name = "telefone_padrao")
    @Getter
    @Setter
    private Boolean padrao = false;

    @ManyToOne
    @JoinColumn(name = "pessoa_propietaria")
    @Getter @Setter private Pessoa pessoa;

    @Override
    public Long getId() {
        return idTelefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Telefone)) return false;

        Telefone telefone = (Telefone) o;

        return idTelefone != null ? idTelefone.equals(telefone.idTelefone) : telefone.idTelefone == null;
    }

    @Override
    public int hashCode() {
        return idTelefone != null ? idTelefone.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Telefone{" +
                "idTelefone=" + idTelefone +
                ", numero='" + numero + '\'' +
                ", tipo=" + tipo +
                ", observacao='" + observacao + '\'' +
                ", pessoa=" + pessoa +
                '}';
    }
}
