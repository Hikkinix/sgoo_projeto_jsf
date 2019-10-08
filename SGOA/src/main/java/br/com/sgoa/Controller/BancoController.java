/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Banco;
import br.com.sgoa.Facade.BancoFacade;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class BancoController extends AbstractController<Banco> implements Serializable {

    @Inject
    private BancoFacade ejbFacade;

    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
       return Banco.class;
    }

    @Override
    protected String getAutoComplete() {
        return "nomeBanco";
    }

}
