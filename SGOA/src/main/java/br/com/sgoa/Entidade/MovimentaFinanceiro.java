package br.com.sgoa.Entidade;

import br.com.sgoa.Enums.OrigemMovimento;
import br.com.sgoa.Enums.StatusCompra;
import br.com.sgoa.Enums.TipoMovimento;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "movimentacaoFinanceiro")
public class MovimentaFinanceiro implements Serializable, EntidadePai{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long idMovimento;

    @Column(name = "movimento_idOrigem", nullable = false)
    @Getter @Setter
    private Long idOrigem;

    @Column(name = "movimento_valor")
    @Getter
    @Setter
    private BigDecimal valorMovimento = BigDecimal.ZERO;

    @Column(name = "movimento_data")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date dataMovimento = new Date();

    @JoinColumn(name = "movimento_origem")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private OrigemMovimento origemMovimento = OrigemMovimento.NA;

    @JoinColumn(name = "movimento_origem")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoMovimento tipoMovimento;

    @JoinColumn(name = "usuario", referencedColumnName = "idUsuario")
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Usuario usuario;

    @JoinColumn(name = "receberPagar", referencedColumnName = "idReceberPagar")
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private ReceberPagar receberPagar;

    @JoinColumn(name = "conta", referencedColumnName = "idContaBancaria")
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private ContaBancaria conta;

    @Override
    public Long getId() {
        return idMovimento;
    }

    public MovimentaFinanceiro() {
    }

    public MovimentaFinanceiro(MovimentaFinanceiro mov) {
        this.idOrigem = mov.getIdOrigem();
        this.conta = mov.getConta();
        this.valorMovimento = mov.getValorMovimento();
        this.dataMovimento = mov.getDataMovimento();
        this.origemMovimento = mov.getOrigemMovimento();
        this.tipoMovimento = mov.getTipoMovimento();
        this.usuario = mov.getUsuario();
    }

}
