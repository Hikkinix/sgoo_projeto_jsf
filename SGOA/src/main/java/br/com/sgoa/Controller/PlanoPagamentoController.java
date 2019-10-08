/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Estado;
import br.com.sgoa.Entidade.FormaPagamento;
import br.com.sgoa.Enums.TipoPlanoPagamento;
import br.com.sgoa.Facade.EstadoFacade;
import br.com.sgoa.Facade.PlanoPagamentoFacade;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PlanoPagamentoController extends AbstractController<FormaPagamento> implements Serializable {

    @Inject
    private PlanoPagamentoFacade ejbFacade;

    public Boolean disableQtdParcelas(){
        if (super.getSelected() != null && super.getSelected().getTipoPlano().equals(TipoPlanoPagamento.AVISTA)){
            return true;
        }
        return false;
    }

    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
       return FormaPagamento.class;
    }

    @Override
    protected String getAutoComplete() {
        return "nomeFormPag";
    }

}
