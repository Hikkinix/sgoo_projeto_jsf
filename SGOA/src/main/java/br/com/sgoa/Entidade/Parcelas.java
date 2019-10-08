package br.com.sgoa.Entidade;


import br.com.sgoa.Enums.TipoPlanoPagamento;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Table(name = "Parcelas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 1, discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("P")
public class Parcelas implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long idParcelas;

    @Column(name = "numero_parcela")
    @Getter @Setter
    private Integer numParcela = 0;

    @Column(name = "numero_atual_parcela")
    @Getter
    @Setter
    private String numAtualParcela = "";

    @Column(name = "max_parcela")
    @Setter
    private Integer maxParcelas = 0;

    @Column(name = "diaVencimento")
    @Getter @Setter
    private Integer diaVencimento = 10;

    @Column(name = "possui_entrada")
    @Getter @Setter
    private  boolean possuiEntrada = false;

    @Column(name = "primeira_parcela")
    @Getter @Setter
    private  BigDecimal valorPrimeiraParcela = BigDecimal.ZERO;

    @Column(name = "valorParcela")
    @Getter @Setter
    private  BigDecimal valorParcela = BigDecimal.ZERO;

    @Getter @Setter
    private TipoPlanoPagamento tipoPlano;

    @JoinColumn(name = "formaPaga", referencedColumnName = "idFormaPag")
    @Getter @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    private FormaPagamento formaPaga;

    public Integer getMaxParcelas() {
        return formaPaga.getNumParcelas();
    }

    @Override
    public Long getId() {
        return idParcelas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcelas parcelas = (Parcelas) o;
        return idParcelas == parcelas.idParcelas;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParcelas);
    }
}
