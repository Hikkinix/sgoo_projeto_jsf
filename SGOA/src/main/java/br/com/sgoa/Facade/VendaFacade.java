package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Venda;
import br.com.sgoa.Entidade.ItemVenda;
import br.com.sgoa.Enums.TipoVenda;
import br.com.sgoa.util.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Transacional
public class VendaFacade extends AbstractFacade<Venda> {

    @Inject
    private EntityManager em;

    @Inject
    private ProdutoFacade produtoFacade;

    public VendaFacade() {
        super(Venda.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Venda salvar(Venda objeto) throws Exception {
        try {
            if (objeto.getVendaTipo().equals(TipoVenda.FATURAMENTO)) {
                movimentaEstoque(objeto);
            }
           return super.salvar(objeto);
        } catch (Exception e) {
            throw new Exception("Erro aaaaaaao salvar Venda", e);
        }
    }

    public void movimentaEstoque(Venda objeto) throws Exception {
        for (ItemVenda item : objeto.getItemVendaList()) {
            removerEstoqueProduto(item);
        }
    }

    public void estorno(Venda objeto) throws Exception {
        try {
            if (objeto.getVendaTipo().equals(TipoVenda.FATURAMENTO)) {
                movimentaEstoqueEstorno(objeto);
                objeto.setVendaTipo(TipoVenda.PEDIDO);
            super.salvar(objeto);
            }
        } catch (Exception e) {
            throw new Exception("Erro aaaao salvar Venda", e);
        }
    }

    public void movimentaEstoqueEstorno(Venda objeto) throws Exception {
        for (ItemVenda item : objeto.getItemVendaList()) {
                incluirEstoqueProduto(item);
        }
    }


    public void incluirEstoqueProduto(ItemVenda item) {
        item.getProduto().setQuantidade(item.getQuantidade().add(item.getProduto().getQuantidade()));
        produtoFacade.getEntityManager().merge(item.getProduto());
    }

    public void removerEstoqueProduto(ItemVenda item) throws Exception {
        try {
            if (item.getProduto().getQuantidade().compareTo(item.getQuantidade()) >= 0) {
                item.getProduto().setQuantidade(item.getProduto().getQuantidade().subtract(item.getQuantidade()));
                produtoFacade.salvar(item.getProduto());
            } else {

                throw new Exception("Estoque Insuficiente");
            }

        } catch (Exception e) {
            throw new Exception("Erro item venda:");
        }
    }
}
