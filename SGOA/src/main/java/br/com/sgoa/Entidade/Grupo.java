package br.com.sgoa.Entidade;


import br.com.sgoa.Enums.TipoGrupo;
import javax.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Grupo implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private Long idGrupo;

    @Column(name = "nome_grupo", nullable = false)
    @Getter @Setter private String nomeGrupo;

    @Column(name = "ativa_grupo", nullable = false)
    @Getter @Setter private Boolean grupoAtivo = true;

    @JoinColumn(name = "grupo_pai")
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @Getter @Setter private Grupo grupoPai;

    @Column(name = "tipo_grupo", nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter @Setter private TipoGrupo tipoGrupo;

    @Override
    public Long getId() {
        return idGrupo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grupo)) return false;

        Grupo grupo = (Grupo) o;

        if (idGrupo != null ? !idGrupo.equals(grupo.idGrupo) : grupo.idGrupo != null) return false;
        return tipoGrupo == grupo.tipoGrupo;
    }

    @Override
    public int hashCode() {
        int result = idGrupo != null ? idGrupo.hashCode() : 0;
        result = 31 * result + (tipoGrupo != null ? tipoGrupo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "idGrupo=" + idGrupo +
                ", nomeGrupo='" + nomeGrupo + '\'' +
                ", tipoGrupo=" + tipoGrupo +
                '}';
    }
}
