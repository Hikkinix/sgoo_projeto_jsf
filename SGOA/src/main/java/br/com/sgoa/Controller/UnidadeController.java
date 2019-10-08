/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Unidade;
import br.com.sgoa.Facade.UnidadeFacade;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class UnidadeController extends AbstractController<Unidade> implements Serializable {

    @Inject
    private UnidadeFacade ejbFacade;

    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
       return Unidade.class;
    }

    @Override
    protected String getAutoComplete() {
        return "nomeUnidade";
    }

}
