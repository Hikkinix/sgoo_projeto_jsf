package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Compra;
import br.com.sgoa.Entidade.ItemCompra;
import br.com.sgoa.Entidade.ParcelasCompra;
import br.com.sgoa.Enums.TipoCompra;
import br.com.sgoa.util.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Transacional
public class CompraFacade extends AbstractFacade<Compra> {

    @Inject
    private EntityManager em;

    @Inject
    private ProdutoFacade produtoFacade;

    public CompraFacade() {
        super(Compra.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Compra salvar(Compra objeto) throws Exception {
        try {
            if (objeto.getCompraTipo().equals(TipoCompra.FATURAMENTO)) {
                movimentaEstoque(objeto);
            }
           return super.salvar(objeto);
        } catch (Exception e) {
            throw new Exception("Erro aaaaaaao salvar Compra", e);
        }
    }

    public void movimentaEstoque(Compra objeto) {
        for (ItemCompra item : objeto.getItemCompraList()) {
            incluirEstoqueProduto(item);
        }
    }

    public void estorno(Compra objeto) throws Exception {
        try {
            if (objeto.getCompraTipo().equals(TipoCompra.FATURAMENTO)) {
                movimentaEstoqueEstorno(objeto);
                objeto.setCompraTipo(TipoCompra.PEDIDO);
            super.salvar(objeto);
            }
        } catch (Exception e) {
            throw new Exception("Erro aaaao salvar Compra", e);
        }
    }

    public void movimentaEstoqueEstorno(Compra objeto) throws Exception {
        for (ItemCompra item : objeto.getItemCompraList()) {
                removerEstoqueProduto(item);
        }
    }


    public void incluirEstoqueProduto(ItemCompra item) {
        item.getProduto().setQuantidade(item.getQuantidade().add(item.getProduto().getQuantidade()));
        item.getProduto().setPrecoUltimaCompra(item.getPrecoUnitario());
        item.getProduto().setFornecedorProduto(item.getCompra().getFornecedor());
        produtoFacade.getEntityManager().merge(item.getProduto());
    }

    public void removerEstoqueProduto(ItemCompra item) throws Exception {
        try {
            if (item.getProduto().getQuantidade().compareTo(item.getQuantidade()) >= 0) {
                item.getProduto().setQuantidade(item.getProduto().getQuantidade().subtract(item.getQuantidade()));
                produtoFacade.getEntityManager().merge(item.getProduto());
            }
        } catch (Exception e) {
            throw new Exception("Erro ao estornar Produto");
        }
    }
}
