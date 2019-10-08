/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Banco;
import br.com.sgoa.Entidade.ContaBancaria;
import br.com.sgoa.Facade.BancoFacade;
import br.com.sgoa.Facade.ContaBancariaFacade;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ContaBancariaController extends AbstractController<ContaBancaria> implements Serializable {

    @Inject
    private ContaBancariaFacade ejbFacade;

    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
       return ContaBancaria.class;
    }

    @Override
    protected String getAutoComplete() {
        return "numeroConta";
    }

}
