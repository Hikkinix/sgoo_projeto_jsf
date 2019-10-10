/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Compra;
import br.com.sgoa.Entidade.ReceberPagar;
import br.com.sgoa.Enums.StatusReceberPagar;
import br.com.sgoa.Enums.TipoCompra;
import br.com.sgoa.Enums.TipoReceberPagar;
import br.com.sgoa.Facade.ReceberPagarFacade;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Named
@ViewScoped
public class ContasPagarController extends AbstractController<ReceberPagar> implements Serializable {

    @Inject
    private ReceberPagarFacade ejbFacade;

    @Getter
    @Setter
    private boolean ativarPagamento = false;


    @Getter
    @Setter
    private boolean novoPagamento = false;

    public void pagamento(ReceberPagar teste) {
        super.setSelected(teste);
        if (super.getSelected() != null) {
            ativarPagamento = true;
            super.setAtivarCreate(false);
            super.setAtivarList(false);
            super.setAtivarView(false);
        } else {
            System.out.println("Selecione uma conta");
        }
    }

    @Override
    public void salvar() {
        try {
            super.getSelected().setTipoPlano(super.getSelected().getFormaPaga().getTipoPlano());
            if (super.getSelected().getValorTotalPagar().compareTo(BigDecimal.ZERO) == 0) {
                super.getSelected().setValorTotalPagar(super.getSelected().getValorPagar());
            }
            ejbFacade.salvar(super.getSelected());
        } catch (Exception e) {
            errorMensagem("Erro:" + e);
        }
    }

    public void pagar() {
        try {

            super.getSelected().setTipoPlano(super.getSelected().getFormaPaga().getTipoPlano());

            if (super.getSelected().getValorRestate().compareTo(BigDecimal.ZERO) == 0) {
                super.getSelected().setStatus(StatusReceberPagar.PAGA);
            } else {
                ejbFacade.pagoPacrcial(super.getSelected());
            }
           // ejbFacade.efetuarPagamento(super.getSelected());
            ativarPagamento = false;
            salvar();
        } catch (Exception e) {
            errorMensagem("Erro:" + e);
        }
    }

    public void estorno(ReceberPagar obj) {
        try {
            super.setSelected(obj);
            salvar();
            ejbFacade.estornar(super.getSelected());
            layoutList();
        } catch (Exception e) {
            errorMensagem("Erro:" + e);
        }
    }


    public void cancelar(ReceberPagar obj) {
        try {
            super.setSelected(obj);
            super.getSelected().setStatus(StatusReceberPagar.CANCELADA);
            ejbFacade.estornar(super.getSelected());
            salvar();
            layoutList();
        } catch (Exception e) {
            errorMensagem("Erro:" + e);
        }
    }


    public List<ReceberPagar> listaPaga() {
        return ejbFacade.listaFiltroPagar("status", StatusReceberPagar.PAGA.name());
    }

    public List<ReceberPagar> listaCancelada() {
        return ejbFacade.listaFiltroPagar("status", StatusReceberPagar.CANCELADA.name());
    }

    public List<ReceberPagar> listaProcessamento() {
        return ejbFacade.listaFiltroPagar("status", StatusReceberPagar.GERADA.name(), StatusReceberPagar.PROCESSAMENTO.name());
    }


    @Override
    public void layoutList() {
        ativarPagamento = false;
        super.setAtivarCreate(false);
        super.setAtivarView(false);
        super.setAtivarList(true);
    }

    public void novoContaPaga() {
        super.novo();
        novoPagamento = true;
        ativarPagamento = true;
        super.setAtivarCreate(false);
        super.setAtivarView(false);
        super.setAtivarList(false);

        super.getSelected().setTipoReceberPagar(TipoReceberPagar.PAGAR);
        super.getSelected().setStatus(StatusReceberPagar.PAGA);
    }

    public void novoContaPagar() {
        super.novo();
        super.getSelected().setTipoReceberPagar(TipoReceberPagar.PAGAR);
        super.getSelected().setStatus(StatusReceberPagar.GERADA);
    }

    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
        return ReceberPagar.class;
    }

    @Override
    protected String getAutoComplete() {
        return "idReceberPagar";
    }
}
