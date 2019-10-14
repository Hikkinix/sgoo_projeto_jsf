/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.*;
import br.com.sgoa.Enums.*;
import br.com.sgoa.Facade.*;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;


@Named
@ViewScoped
public class CompraController extends AbstractController<Compra> implements Serializable {

    @Inject
    private CompraFacade ejbFacade;
    @Inject
    private ParcelasFacade parcelasFacade;
    @Inject
    private PlanoPagamentoFacade planoPagamentoFacade;

    @Inject
    private ReceberPagarFacade receberPagarFacade;
    @Inject
    private PessoaFacade pessoaFacade;

    @Getter
    @Setter
    private ItemCompra itemCompra = new ItemCompra();
    @Getter
    @Setter
    private ParcelasCompra parcelas = new ParcelasCompra();
    private Boolean faturarAoSalvar = false;

    public void addItemCompra() {
        if (validaAddItem()) {
            super.getSelected().setItemCompra(itemCompra);
            super.getSelected().addItemCompra();
            itemCompra = new ItemCompra();
        }
    }

    public Boolean validaAddItem() {
        if (itemCompra.getProduto() == null) {
            errorMensagem("Escolha um Produto");
            return false;
        } else if (itemCompra.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            errorMensagem("Quantidade invalida!");
            return false;
        } else if (itemCompra.getPrecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            errorMensagem("Preco unitario do produto invalida!");
            return false;
        } else if (itemCompra.getDesconto().compareTo(itemCompra.getPrecoUnitario()) > 0) {
            errorMensagem("Desconto Invalido: Maior que o valor do item");
            return false;
        }
        return true;
    }

    public void informarProduto() {
        if (itemCompra.getProduto() != null) {
            itemCompra.setPrecoUnitario(itemCompra.getProduto().getPrecoCusto());
        }
    }

    public void salvarParcelasCompra() {
        try {
            super.getSelected().gerarParcelas(parcelas);
            for (ParcelasCompra parcela : super.getSelected().getParcelasCompra()) {
                parcelasFacade.salvar(parcela);
            }
        } catch (Exception e) {
            errorMensagem("Erro:" + e);
        }
    }

    public List<FormaPagamento> listaFiltrandoPlano(String filtro) {
        return planoPagamentoFacade.listaFiltrandoPlano(parcelas.getTipoPlano(), filtro, getAutoComplete());
    }

    public void gerarContasPagar() {
        try {
            Integer i = 0;
            for (ParcelasCompra parcela : super.getSelected().getParcelasCompra()) {
                ReceberPagar contaPagar = new ReceberPagar();

                Calendar cal = Calendar.getInstance();
                cal.setTime(contaPagar.getDiaVencimento());
                cal.add(Calendar.MONTH, 1);
                cal.add(Calendar.DAY_OF_MONTH, parcela.getDiaVencimento());

                contaPagar.setPessoa(super.getSelected().getFornecedor());
                contaPagar.setDiaVencimento(cal.getTime());
                contaPagar.setNumParcela(parcela.getNumAtualParcela());
                contaPagar.setTotalParcelas(parcela.getNumParcela());
                contaPagar.setTipoPlano(parcela.getFormaPaga().getTipoPlano());
                contaPagar.setValorPagar(parcela.getValorParcela());
                contaPagar.setValorTotalPagar(super.getSelected().getTotalLiquido());
                contaPagar.setTipoPagar(TipoContaPagar.COMPRA);
                contaPagar.setTipoReceberPagar(TipoReceberPagar.PAGAR);
                contaPagar.setParcelaCompra(parcela);

                if (parcela.getFormaPaga().getTipoPlano().equals(TipoPlanoPagamento.APRAZO)) {
                    contaPagar.setStatus(StatusReceberPagar.PROCESSAMENTO);
                } else {
                    contaPagar.setValorLiquidar(super.getSelected().getTotalLiquido());
                    contaPagar.setStatus(StatusReceberPagar.PAGA);
                }

                if ((parcela.isPossuiEntrada() || parcelas.getValorPrimeiraParcela().compareTo(BigDecimal.ZERO) > 0)
                        && i == 0) {
                    contaPagar.setValorPagar(parcela.getValorPrimeiraParcela());
                    contaPagar.setStatus(StatusReceberPagar.PAGA);
                }

                receberPagarFacade.salvar(contaPagar);
                i++;
            }
        } catch (Exception e) {
            errorMensagem("Erro:" + e);
        }
    }

    public List<Compra> listaCompraPedido() {
        return ejbFacade.listaFiltro("compraTipo", TipoCompra.PEDIDO.name());
    }

    public List<Compra> listaCompraCancelada() {
        return ejbFacade.listaFiltro("compraTipo", TipoCompra.CANCELADA.name());
    }

    public List<Compra> listaCompraFaturamento() {
        return ejbFacade.listaFiltro("compraTipo", TipoCompra.FATURAMENTO.name());
    }

    public void novoPedido() {
        super.novo();
        super.getSelected().setCompraTipo(TipoCompra.PEDIDO);
//        parcelas.setCompra(super.getSelected());
    }

    public void novoFaturamento() {
        super.novo();
        super.getSelected().setCompraTipo(TipoCompra.FATURAMENTO);
//        parcelas.setCompra(super.getSelected());
    }

    public void cancelarCompra(Compra obj) {
        try {
            super.setSelected(obj);
            if (super.getSelected().getCompraTipo().equals(TipoCompra.FATURAMENTO)) {
                ejbFacade.estorno(super.getSelected());
            }
            super.getSelected().setCompraTipo(TipoCompra.CANCELADA);
            super.getSelected().setStatusCompra(StatusCompra.ENCERADA);
        } catch (Exception e) {
            errorMensagem("Erro:" + e);
        }
    }

    @Override
    public void salvar() {
        try {
            if (faturarAoSalvar) {
                super.getSelected().setCompraTipo(TipoCompra.FATURAMENTO);
            }

            if (super.getSelected().getCompraTipo().equals(TipoCompra.FATURAMENTO)) {
                salvarParcelasCompra();
                gerarContasPagar();
            }

            ejbFacade.salvar(super.getSelected());
            super.layoutList();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void editar(Compra e) {
        super.editar(e);
    }

    public void faturar(Compra e) {
        super.editar(e);
        parcelas.setCompra(super.getSelected());
        faturarAoSalvar = true;
    }

    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
        return Compra.class;
    }

    @Override
    protected String getAutoComplete() {
        return "idCompra";
    }

}
