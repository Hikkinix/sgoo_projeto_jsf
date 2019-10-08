package br.com.sgoa.Entidade;

import br.com.sgoa.Enums.StatusCompra;
import br.com.sgoa.Enums.TipoCompra;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Compra implements Serializable, EntidadePai, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long idCompra;

    @Column(name = "compra_data_compra")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date dataCompra = new Date();

    @Column(name = "compra_data_faturamento")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date dataFaturamento = new Date();

    @Column(name = "compra_total_bruto")
    @Setter
    private BigDecimal totalBruto = BigDecimal.ZERO;

    @Column(name = "compra_total_liquido")
    @Setter
    private BigDecimal totalLiquido = BigDecimal.ZERO;

    @Column(name = "compra_desconto_total")
    @Setter
    private BigDecimal DescontoTotal = BigDecimal.ZERO;

    @Column(name = "compra_num_nota_fiscal")
    @Getter
    @Setter
    private String nNotaFiscal = "";


    @Column(name = "compra_possui_frete")
    @Getter
    @Setter
    private boolean possuiFrete = false;

    @Column(name = "compra_valor_frete")
    @Getter
    @Setter
    private BigDecimal valorFrete = BigDecimal.ZERO;

    @JoinColumn(name = "fornecedor", referencedColumnName = "idPessoa")
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Pessoa fornecedor;

    @JoinColumn(name = "funcionario", referencedColumnName = "idUsuario")
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Usuario funcionario;

    @JoinColumn(name = "status_compra")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private StatusCompra statusCompra = StatusCompra.PEDIDO;

    @JoinColumn(name = "status_compra")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoCompra compraTipo = TipoCompra.PEDIDO;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "compra",
            fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<ItemCompra> itemCompraList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "compra",
            fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<ParcelasCompra> parcelasCompra = new ArrayList<>();


    @Column(name = "compra_desconto")
    @Getter
    @Setter
    private BigDecimal descontoTotalMonetario = BigDecimal.ZERO;


    @Column(name = "compra_desconto_Porcentagem")
    @Getter
    @Setter
    private BigDecimal descontoTotalPorcentagem = BigDecimal.ZERO;

    @Transient
    @Getter
    @Setter
    private ItemCompra itemCompra = new ItemCompra();

    public void addItemCompra() {
        if (itemCompra != null) {
            if (!itemCompraList.contains(itemCompra)) {
                itemCompraList.add(itemCompra);
                itemCompra.setCompra(this);
            }
        }
    }

    @NotNull
    public void removeItemCompra(ItemCompra item) {
        if (itemCompraList.contains(item)) {
            itemCompraList.remove(item);
        }
    }

    public void gerarParcelas(ParcelasCompra parcelas) {
        Integer i = 0;
        if (parcelas.getNumParcela() != null) {
            if (parcelas.getNumParcela().equals(0)) {
                parcelas.setNumParcela(1);
            }
            setParcelasCompra(new ArrayList<ParcelasCompra>());
            for (i = 0; i < parcelas.getNumParcela(); i++) {
                ParcelasCompra parcelasCompra = new ParcelasCompra();
                parcelasCompra = parcelas;
                parcelasCompra.setMaxParcelas(parcelas.getNumParcela());
                parcelasCompra.setNumParcela(parcelas.getNumParcela());
                parcelasCompra.setNumAtualParcela(i.toString() + "/" + parcelas.getNumParcela());
                parcelasCompra.setDiaVencimento(parcelas.getDiaVencimento());
                parcelasCompra.setFormaPaga(parcelas.getFormaPaga());
                parcelasCompra.setPossuiEntrada(false);
                parcelasCompra.setTipoPlano(parcelas.getTipoPlano());
//                parcelasCompra.setCompra(this);

                BigDecimal result = getTotalLiquido().subtract(parcelas.getValorPrimeiraParcela());
                result = result.divide(
                        new BigDecimal(parcelas.getNumParcela()))
                        .setScale(2, RoundingMode.HALF_DOWN);

                parcelasCompra.setValorParcela(result);

                if ((parcelas.isPossuiEntrada() || parcelas.getValorPrimeiraParcela().compareTo(BigDecimal.ZERO) > 0)
                        && i == 0) {
                    parcelasCompra.setPossuiEntrada(true);
                    parcelasCompra.setValorParcela(parcelasCompra.getValorPrimeiraParcela());
                    getParcelasCompra().add(parcelasCompra);
                }

                getParcelasCompra().add(parcelasCompra);
            }
        }
    }

    public BigDecimal getTotalBruto() {
        totalBruto = BigDecimal.ZERO;
        if (itemCompraList != null && !itemCompraList.isEmpty()) {
            for (ItemCompra item : itemCompraList) {
                totalBruto = totalBruto.add(item.getPrecoBruto());
            }
        }
        return totalBruto;
    }

    public BigDecimal getTotalLiquido() {
        totalLiquido = BigDecimal.ZERO;
        if (itemCompraList != null && !itemCompraList.isEmpty()) {
            for (ItemCompra item : itemCompraList) {
                totalLiquido = totalLiquido.add(item.getPrecoLiquido());
            }
        }
        return totalLiquido;
    }

    public BigDecimal getDescontoTotal() {
        DescontoTotal = BigDecimal.ZERO;
        if (itemCompraList != null && !itemCompraList.isEmpty()) {
            for (ItemCompra item : itemCompraList) {
                DescontoTotal = DescontoTotal.add(item.getDesconto());
            }
        }
        return DescontoTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compra compra = (Compra) o;
        return idCompra == compra.idCompra;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompra);
    }

    @Override
    public String toString() {
        return idCompra.toString();
    }

    @Override
    public Long getId() {
        return idCompra;
    }
}
