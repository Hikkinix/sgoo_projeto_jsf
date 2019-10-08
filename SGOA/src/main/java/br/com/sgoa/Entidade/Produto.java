package br.com.sgoa.Entidade;

import br.com.sgoa.Enums.TipoProduto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name = "Produto")
public class Produto implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private long idProduto;

    @Column(name = "produto_nome", nullable = false)
    @Getter @Setter private String nomeProduto;

    @Column(name = "produto_qtd")
    @Getter @Setter private BigDecimal quantidade;

    @Column(name = "produto_qtd_min")
    @Getter @Setter private BigDecimal qtdeMinima;

    @JoinColumn(name = "produto_un")
    @ManyToOne(cascade = CascadeType.MERGE)
    @Getter @Setter private Unidade unidade;

    @Column(name = "produto_codigo")
    @Getter @Setter private String codigoFabricante;

    @ManyToOne
    @JoinColumn(name = "produto_marca")
    @Getter @Setter private Marca marca;

    @ManyToOne
    @JoinColumn(name = "produto_grupo")
    @Getter @Setter private Grupo grupoProduto;

    @Column(name = "produto_preco_custo")
    @Getter @Setter private BigDecimal precoCusto =  new BigDecimal(BigInteger.ZERO);

    @Column(name = "produto_preco_venda")
    @Getter @Setter private BigDecimal precoVenda = new BigDecimal(BigInteger.ZERO);

    @Column(name = "produto_preco_ultimacompra")
    @Getter @Setter private BigDecimal precoUltimaCompra =  new BigDecimal(BigInteger.ZERO);

    @Column(name = "produto_ativo")
    @Getter @Setter private Boolean produtoAtivo;

    @Column(name = "produto_tipo")
    @Enumerated(EnumType.STRING)
    @Getter @Setter private TipoProduto tipoProduto = TipoProduto.PRODUTO_VENDA;

    @Override
    public Long getId() {
        return idProduto;
    }

    public Boolean getEstoqueSeg() {
        return qtdeMinima.compareTo(quantidade) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;

        Produto produto = (Produto) o;

        return idProduto == produto.idProduto;
    }

    @Override
    public int hashCode() {
        return (int) (idProduto ^ (idProduto >>> 32));
    }

    @Override
    public String toString() {
        return "Produto{" +
                "idProduto=" + idProduto +
                ", nomeProduto='" + nomeProduto + '\'' +
                '}';
    }
}
