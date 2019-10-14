package br.com.sgoa.Entidade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class ItemCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long idItemCompra;

    @Column(name = "item_preco_unitario")
    @Getter @Setter
    private BigDecimal precoUnitario = BigDecimal.ZERO;

    @Column(name = "item_preco_bruto")
    @Setter
    private BigDecimal precoBruto = BigDecimal.ZERO;

    @Column(name = "item_preco_liquido")
    @Setter
    private BigDecimal precoLiquido = BigDecimal.ZERO;

    @Column(name = "item_desconto_compra")
    @Getter @Setter
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column(name = "item_quantidade")
    @Getter @Setter
    private BigDecimal quantidade = BigDecimal.ZERO;

    @JoinColumn(name = "produto")
    @ManyToOne
    @Getter @Setter
    private Produto produto;

    @JoinColumn(name = "compra", referencedColumnName = "idCompra")
    @ManyToOne
    @Getter @Setter
    private Compra compra;

    public ItemCompra() {
    }

    public ItemCompra(ItemCompra item) {
        this.precoUnitario = item.getPrecoUnitario();
        this.desconto = item.getDesconto();
        this.quantidade = item.getQuantidade();
        this.produto = item.getProduto();
        this.compra = item.getCompra();
    }

    public BigDecimal getPrecoBruto() {
        return getQuantidade().multiply(getPrecoUnitario());
    }

    public BigDecimal getPrecoLiquido() {
        return getPrecoBruto().subtract(getDesconto());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCompra that = (ItemCompra) o;
        return idItemCompra == that.idItemCompra &&
                Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItemCompra, produto);
    }

    @Override
    public String toString() {
        return "ItemCompra{" +
                "idItemCompra=" + idItemCompra +
                '}';
    }
}
