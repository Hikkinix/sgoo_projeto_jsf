package br.com.sgoa.Entidade;

import br.com.sgoa.Enums.StatusVenda;
import br.com.sgoa.Enums.TipoVenda;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Venda implements Serializable, EntidadePai, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long idVenda;

    @Column(name = "venda_data_venda")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date dataVenda = new Date();

    @Column(name = "venda_data_faturamento")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date dataFaturamento = new Date();

    @Column(name = "venda_total_bruto")
    @Setter
    private BigDecimal totalBruto = BigDecimal.ZERO;

    @Column(name = "venda_total_liquido")
    @Setter
    private BigDecimal totalLiquido = BigDecimal.ZERO;

    @Column(name = "venda_desconto_total")
    @Setter
    private BigDecimal DescontoTotal = BigDecimal.ZERO;

    @Column(name = "venda_num_nota_fiscal")
    @Getter
    @Setter
    private String nNotaFiscal = "";


    @Column(name = "venda_possui_frete")
    @Getter
    @Setter
    private boolean possuiFrete = false;

    @Column(name = "venda_valor_frete")
    @Getter
    @Setter
    private BigDecimal valorFrete = BigDecimal.ZERO;

    @JoinColumn(name = "cliente", referencedColumnName = "idPessoa")
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Pessoa cliente;

    @JoinColumn(name = "funcionario", referencedColumnName = "idUsuario")
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Usuario funcionario;

    @JoinColumn(name = "status_venda")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private StatusVenda statusVenda = StatusVenda.PEDIDO;

    @JoinColumn(name = "status_venda")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoVenda vendaTipo = TipoVenda.PEDIDO;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "venda",
            fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<ItemVenda> itemVendaList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "venda",
            fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<ParcelasVenda> parcelasVenda = new ArrayList<>();


    @Column(name = "venda_desconto")
    @Getter
    @Setter
    private BigDecimal descontoTotalMonetario = BigDecimal.ZERO;


    @Column(name = "venda_desconto_Porcentagem")
    @Getter
    @Setter
    private BigDecimal descontoTotalPorcentagem = BigDecimal.ZERO;

    @Transient
    @Getter
    @Setter
    private ItemVenda itemVenda = new ItemVenda();

    public void addItemVenda() {
        if (itemVenda != null) {
            if (!itemVendaList.contains(itemVenda)) {
                itemVendaList.add(itemVenda);
                itemVenda.setVenda(this);
            }
        }
    }

    public void removeItemVenda(ItemVenda item) {
        if (itemVendaList.contains(item)) {
            itemVendaList.remove(item);
        }
    }

    public void gerarParcelas(ParcelasVenda parcelas) {
        Integer i = 0;
        if (parcelas.getNumParcela() != null) {
            if (parcelas.getNumParcela().equals(0)) {
                parcelas.setNumParcela(1);
            }
            setParcelasVenda(new ArrayList<ParcelasVenda>());
            for (i = 0; i < parcelas.getNumParcela(); i++) {
                ParcelasVenda parcelasVenda = new ParcelasVenda();
                parcelasVenda = parcelas;
                parcelasVenda.setMaxParcelas(parcelas.getNumParcela());
                parcelasVenda.setNumParcela(parcelas.getNumParcela());
                parcelasVenda.setNumAtualParcela(i.toString() + "/" + parcelas.getNumParcela());
                parcelasVenda.setDiaVencimento(parcelas.getDiaVencimento());
                parcelasVenda.setFormaPaga(parcelas.getFormaPaga());
                parcelasVenda.setPossuiEntrada(false);
                parcelasVenda.setTipoPlano(parcelas.getTipoPlano());
//                parcelasVenda.setVenda(this);

                BigDecimal result = getTotalLiquido().subtract(parcelas.getValorPrimeiraParcela());
                result = result.divide(
                        new BigDecimal(parcelas.getNumParcela()))
                        .setScale(2, RoundingMode.HALF_DOWN);

                parcelasVenda.setValorParcela(result);

                if ((parcelas.isPossuiEntrada() || parcelas.getValorPrimeiraParcela().compareTo(BigDecimal.ZERO) > 0)
                        && i == 0) {
                    parcelasVenda.setPossuiEntrada(true);
                    parcelasVenda.setValorParcela(parcelasVenda.getValorPrimeiraParcela());
                    getParcelasVenda().add(parcelasVenda);
                }

                getParcelasVenda().add(parcelasVenda);
            }
        }
    }

    public BigDecimal getTotalBruto() {
        totalBruto = BigDecimal.ZERO;
        if (itemVendaList != null && !itemVendaList.isEmpty()) {
            for (ItemVenda item : itemVendaList) {
                totalBruto = totalBruto.add(item.getPrecoBruto());
            }
        }
        return totalBruto;
    }

    public BigDecimal getTotalLiquido() {
        totalLiquido = BigDecimal.ZERO;
        if (itemVendaList != null && !itemVendaList.isEmpty()) {
            for (ItemVenda item : itemVendaList) {
                totalLiquido = totalLiquido.add(item.getPrecoLiquido());
            }
        }
        return totalLiquido;
    }

    public BigDecimal getDescontoTotal() {
        DescontoTotal = BigDecimal.ZERO;
        if (itemVendaList != null && !itemVendaList.isEmpty()) {
            for (ItemVenda item : itemVendaList) {
                DescontoTotal = DescontoTotal.add(item.getDesconto());
            }
        }
        return DescontoTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return idVenda == venda.idVenda;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVenda);
    }

    @Override
    public String toString() {
        return idVenda.toString();
    }

    @Override
    public Long getId() {
        return idVenda;
    }
}
