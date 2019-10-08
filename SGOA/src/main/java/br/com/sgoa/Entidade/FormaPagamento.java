package br.com.sgoa.Entidade;

import br.com.sgoa.Enums.TipoPlanoPagamento;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class FormaPagamento implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long idFormaPag;

    @Column(name = "forma_pag_nome")
    @Getter @Setter
    private String nomeFormPag;

    @Column(name = "forma_pag_num_parcelas")
    @Getter @Setter
    private Integer numParcelas = 0;

    @Column(name = "forma_pag_tipo")
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private TipoPlanoPagamento tipoPlano = TipoPlanoPagamento.AVISTA;

    @Override
    public Long getId() {
        return idFormaPag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormaPagamento)) return false;

        FormaPagamento that = (FormaPagamento) o;

        return getIdFormaPag() == that.getIdFormaPag();
    }

    @Override
    public int hashCode() {
        return (int) (getIdFormaPag() ^ (getIdFormaPag() >>> 32));
    }

    @Override
    public String toString() {
        return idFormaPag.toString();
    }
}
