/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.ReceberPagar;
import br.com.sgoa.Enums.StatusReceberPagar;
import br.com.sgoa.Enums.TipoReceberPagar;
import br.com.sgoa.Facade.ReceberPagarFacade;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named
@ViewScoped
public class ContasReceberController extends AbstractController<ReceberPagar> implements Serializable {

    @Inject
    private ReceberPagarFacade ejbFacade;

    @Getter
    @Setter
    private boolean ativaRecebimento;

    public void recebimento(ReceberPagar teste) {
        super.setSelected(teste);
        if (super.getSelected() != null) {
            ativaRecebimento = true;
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
            ejbFacade.salvar(super.getSelected());
        } catch (Exception e) {
            errorMensagem("Erro:" + e);
        }
    }

    public void pagar() {
        try {

        super.getSelected().setStatus(StatusReceberPagar.PAGA);
        super.getSelected().setTipoPlano(super.getSelected().getFormaPaga().getTipoPlano());
        ejbFacade.efetuarPagamento(super.getSelected());
        salvar();
        ativaRecebimento = false;
        } catch (Exception e) {
            errorMensagem("Erro:" + e);
        }
    }

    public void estorno(ReceberPagar obj) {
        try {
            super.setSelected(obj);
            super.getSelected().setStatus(StatusReceberPagar.PROCESSAMENTO);
            ejbFacade.estornar(super.getSelected());
            salvar();
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
        return ejbFacade.listaFiltroReceber("status", StatusReceberPagar.PAGA.name());
    }

    public List<ReceberPagar> listaCancelada() {
        return ejbFacade.listaFiltroReceber("status", StatusReceberPagar.CANCELADA.name());
    }

    public List<ReceberPagar> listaProcessamento() {
        return ejbFacade.listaFiltroReceber("status", StatusReceberPagar.PROCESSAMENTO.name(), StatusReceberPagar.PROCESSAMENTO.name());
    }


    @Override
    public void layoutList() {
        ativaRecebimento = false;
        super.setAtivarCreate(false);
        super.setAtivarView(false);
        super.setAtivarList(true);
    }

    public void novoContaRecebida() {
        super.novo();
        super.getSelected().setTipoReceberPagar(TipoReceberPagar.RECEBER);
        super.getSelected().setStatus(StatusReceberPagar.PAGA);
    }

    public void novoContaReceber() {
        super.novo();
        super.getSelected().setTipoReceberPagar(TipoReceberPagar.RECEBER);
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
