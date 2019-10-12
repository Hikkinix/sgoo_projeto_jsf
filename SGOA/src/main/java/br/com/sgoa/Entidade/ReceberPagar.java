package br.com.sgoa.Entidade;


import br.com.sgoa.Enums.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "ReceberPagar")
public class ReceberPagar implements Serializable, EntidadePai {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private long idReceberPagar;

    @Column(name = "numero_parcela")
    @Getter
    @Setter
    private String numParcela = "";

    @Column(name = "total_parcela")
    @Getter
    @Setter
    private Integer totalParcelas;

    @Column(name = "diaVencimento")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date diaVencimento = new Date();

    @JoinColumn(name = "conta", referencedColumnName = "idContaBancaria")
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private ContaBancaria conta;

    @JoinColumn(name = "recebedor", referencedColumnName = "idPessoa")
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Pessoa fornecedorRecebedor;


    @JoinColumn(name = "pagador", referencedColumnName = "idPessoa")
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Pessoa clientePagador;

    @JoinColumn(name = "parcela_compra", referencedColumnName = "idParcelas")
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @Getter
    @Setter
    private ParcelasCompra parcelaCompra;

    @JoinColumn(name = "parcela_venda", referencedColumnName = "idParcelas")
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @Getter
    @Setter
    private ParcelasVenda parcelaVenda;

    @Column(name = "valor_total_pagar")
    @Setter
    private BigDecimal valorTotalPagar = BigDecimal.ZERO;

    @Column(name = "valor_paga")
    @Getter
    @Setter
    private BigDecimal valorPagar = BigDecimal.ZERO;

    @Column(name = "valor_Restante")
    @Setter
    private BigDecimal valorRestate = BigDecimal.ZERO;

    @Column(name = "valor_Liquidar")
    @Getter
    @Setter
    private BigDecimal valorLiquidar = BigDecimal.ZERO;

    @Column(name = "valor_total_pago")
    @Setter
    private BigDecimal valorTotalpago = BigDecimal.ZERO;

    @Column(name = "tipo_plano_pagamento")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoPlanoPagamento tipoPlano;

    @JoinColumn(name = "forma_pagamento", referencedColumnName = "idFormaPag")
    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    private FormaPagamento formaPaga;

    @Column(name = "receber_tipo")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoContaReceber tipoReceber;

    @Column(name = "pagar_tipo")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoContaPagar tipoPagar;

    @Column(name = "status")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private StatusReceberPagar status;

    @Column(name = "tipo")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoReceberPagar tipoReceberPagar;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "receberPagar",
            fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<MovimentaFinanceiro> movimentacoes = new ArrayList<>();


    public ReceberPagar() {
    }

    public ReceberPagar(ReceberPagar receberPagar) {
        this.numParcela = receberPagar.getNumParcela();
        this.totalParcelas = receberPagar.getTotalParcelas();
        this.diaVencimento = receberPagar.getDiaVencimento();
        this.conta = receberPagar.getConta();
        this.fornecedorRecebedor = receberPagar.getFornecedorRecebedor();
        this.clientePagador = receberPagar.getClientePagador();
        this.parcelaCompra = receberPagar.getParcelaCompra();
        this.parcelaVenda = receberPagar.getParcelaVenda();
        this.valorTotalPagar = receberPagar.getValorTotalPagar();
        this.valorPagar = receberPagar.getValorPagar();
        this.valorRestate = receberPagar.getValorRestate();
        this.valorLiquidar = receberPagar.getValorLiquidar();
        this.valorTotalpago = receberPagar.getValorTotalpago();
        this.tipoPlano = receberPagar.getTipoPlano();
        this.formaPaga = receberPagar.getFormaPaga();
        this.tipoReceber = receberPagar.getTipoReceber();
        this.tipoPagar = receberPagar.getTipoPagar();
        this.status = receberPagar.getStatus();
        this.tipoReceberPagar = receberPagar.getTipoReceberPagar();
    }

    public BigDecimal getValorTotalPagar() {
        return valorTotalPagar;
    }

    public BigDecimal getValorTotalpago() {
        BigDecimal valorPago = BigDecimal.ZERO;
        if (movimentacoes != null && !movimentacoes.isEmpty()){
        for (MovimentaFinanceiro mov : movimentacoes)
            valorPago = valorPago.add(mov.getValorMovimento());
        }
        return valorPago;
    }


    public BigDecimal getValorRestate() {
        return valorRestate = getValorTotalPagar().subtract(getValorTotalpago());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceberPagar that = (ReceberPagar) o;
        return idReceberPagar == that.idReceberPagar &&
                Objects.equals(numParcela, that.numParcela) &&
                Objects.equals(parcelaCompra, that.parcelaCompra) &&
                Objects.equals(valorTotalPagar, that.valorTotalPagar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReceberPagar, numParcela, parcelaCompra, valorTotalPagar);
    }

    @Override
    public Long getId() {
        return idReceberPagar;
    }
}
