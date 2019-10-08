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
public class VendaController extends AbstractController<Venda> implements Serializable {

    @Inject
    private VendaFacade ejbFacade;
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
    private ItemVenda itemVenda = new ItemVenda();
    @Getter
    @Setter
    private ParcelasVenda parcelas = new ParcelasVenda();
    private Boolean faturarAoSalvar = false;

    public void addItemVenda() {
        if (validaAddItem()) {
            super.getSelected().setItemVenda(itemVenda);
            super.getSelected().addItemVenda();
            itemVenda = new ItemVenda();
        }
    }

    public Boolean validaAddItem() {
        if (itemVenda.getProduto() == null) {
            errorMensagem("Escolha um Produto");
            return false;
        } else if (itemVenda.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            errorMensagem("Quantidade invalida!");
            return false;
        } else if (itemVenda.getPrecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            errorMensagem("Preco unitario do produto invalida!");
            return false;
        } else if (itemVenda.getDesconto().compareTo(itemVenda.getPrecoUnitario()) > 0) {
            errorMensagem("Desconto Invalido: Maior que o valor do item");
            return false;
        }
        return true;
    }

    public void informarProduto() {
        if (itemVenda.getProduto() != null) {
            itemVenda.setPrecoUnitario(itemVenda.getProduto().getPrecoCusto());
        }
    }

    public void salvarParcelasVenda() {
        try {
            super.getSelected().gerarParcelas(parcelas);
            for (ParcelasVenda parcela : super.getSelected().getParcelasVenda()) {
                parcelasFacade.salvar(parcela);
            }
        } catch (Exception e) {
            errorMensagem("Erro:" + e);
        }
    }

    public List<FormaPagamento> listaFiltrandoPlano(String filtro) {
        return planoPagamentoFacade.listaFiltrandoPlano(parcelas.getTipoPlano(), filtro, getAutoComplete());
    }

    public void gerarContasReceber() {
        try {
            Integer i = 0;
            for (ParcelasVenda parcela : super.getSelected().getParcelasVenda()) {
                ReceberPagar contaReceber = new ReceberPagar();

                Calendar cal = Calendar.getInstance();
                cal.setTime(contaReceber.getDiaVencimento());
                cal.add(Calendar.MONTH, 1);
                cal.add(Calendar.DAY_OF_MONTH, parcela.getDiaVencimento());

                contaReceber.setClientePagador(super.getSelected().getCliente());
                contaReceber.setDiaVencimento(cal.getTime());
                contaReceber.setNumParcela(parcela.getNumAtualParcela());
                contaReceber.setTotalParcelas(parcela.getNumParcela());
                contaReceber.setTipoPlano(parcela.getFormaPaga().getTipoPlano());
                contaReceber.setValorPagar(parcela.getValorParcela());
                contaReceber.setValorTotalPagar(super.getSelected().getTotalLiquido());
                contaReceber.setTipoReceberPagar(TipoReceberPagar.RECEBER);
                contaReceber.setTipoReceber(TipoContaReceber.VENDA);
                contaReceber.setParcelaVenda(parcela);

                if (parcela.getFormaPaga().getTipoPlano().equals(TipoPlanoPagamento.APRAZO)) {
                    contaReceber.setStatus(StatusReceberPagar.PROCESSAMENTO);
                } else {
                    contaReceber.setValorLiquidar(super.getSelected().getTotalLiquido());
                    contaReceber.setStatus(StatusReceberPagar.PAGA);
                }

                if ((parcela.isPossuiEntrada() || parcelas.getValorPrimeiraParcela().compareTo(BigDecimal.ZERO) > 0)
                        && i == 0) {
                    contaReceber.setValorPagar(parcela.getValorPrimeiraParcela());
                    contaReceber.setStatus(StatusReceberPagar.PAGA);
                }

                receberPagarFacade.efetuarPagamento(contaReceber);
                receberPagarFacade.salvar(contaReceber);
                i++;
            }
        } catch (Exception e) {
            errorMensagem("Erro:" + e);
        }
    }

    public List<Venda> listaVendaPedido() {
        return ejbFacade.listaFiltro("vendaTipo", TipoVenda.PEDIDO.name());
    }

    public List<Venda> listaVendaCancelada() {
        return ejbFacade.listaFiltro("vendaTipo", TipoVenda.CANCELADA.name());
    }

    public List<Venda> listaVendaFaturamento() {
        return ejbFacade.listaFiltro("vendaTipo", TipoVenda.FATURAMENTO.name());
    }

    public void novoPedido() {
        super.novo();
        super.getSelected().setVendaTipo(TipoVenda.PEDIDO);
//        parcelas.setVenda(super.getSelected());
    }

    public void novoFaturamento() {
        super.novo();
        super.getSelected().setVendaTipo(TipoVenda.FATURAMENTO);
//        parcelas.setVenda(super.getSelected());
    }

    public void cancelarVenda(Venda obj) {
        try {
            super.setSelected(obj);
            if (super.getSelected().getVendaTipo().equals(TipoVenda.FATURAMENTO)) {
                ejbFacade.estorno(super.getSelected());
            }
            super.getSelected().setVendaTipo(TipoVenda.CANCELADA);
            super.getSelected().setStatusVenda(StatusVenda.ENCERADA);
        } catch (Exception e) {
            errorMensagem("Erro:" + e);
        }
    }

    @Override
    public void salvar() {
        try {
            if (faturarAoSalvar) {
                super.getSelected().setVendaTipo(TipoVenda.FATURAMENTO);
            }
            if (super.getSelected().getVendaTipo().equals(TipoVenda.FATURAMENTO)) {
                salvarParcelasVenda();
                gerarContasReceber();
            }
            ejbFacade.salvar(super.getSelected());
            super.layoutList();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void editar(Venda e) {
        super.editar(e);
    }

    public void faturar(Venda e) {
        super.editar(e);
        parcelas.setVenda(super.getSelected());
        faturarAoSalvar = true;
    }

    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
        return Venda.class;
    }

    @Override
    protected String getAutoComplete() {
        return "idVenda";
    }

}
