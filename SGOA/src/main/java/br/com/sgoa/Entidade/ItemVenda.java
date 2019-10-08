package br.com.sgoa.Entidade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class ItemVenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long idItemVenda;

    @Column(name = "item_preco_unitario")
    @Getter @Setter
    private BigDecimal precoUnitario = BigDecimal.ZERO;

    @Column(name = "item_preco_bruto")
    @Setter
    private BigDecimal precoBruto = BigDecimal.ZERO;

    @Column(name = "item_preco_liquido")
    @Setter
    private BigDecimal precoLiquido = BigDecimal.ZERO;

    @Column(name = "item_desconto_venda")
    @Getter @Setter
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column(name = "item_quantidade")
    @Getter @Setter
    private BigDecimal quantidade = BigDecimal.ZERO;

    @JoinColumn(name = "produto")
    @ManyToOne
    @Getter @Setter
    private Produto produto;

    @JoinColumn(name = "venda", referencedColumnName = "idVenda")
    @ManyToOne
    @Getter @Setter
    private Venda venda;

    public ItemVenda() {
    }

    public ItemVenda(ItemVenda item) {
        this.precoUnitario = item.getPrecoUnitario();
        this.desconto = item.getDesconto();
        this.quantidade = item.getQuantidade();
        this.produto = item.getProduto();
        this.venda = item.getVenda();
    }

    public BigDecimal getPrecoBruto() {
        return getQuantidade().multiply(getPrecoUnitario());
    }

    public BigDecimal getPrecoLiquido() {
        return getPrecoBruto().subtract(getDesconto().multiply(getQuantidade()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemVenda that = (ItemVenda) o;
        return idItemVenda == that.idItemVenda &&
                Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItemVenda, produto);
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "idItemVenda=" + idItemVenda +
                '}';
    }
}
