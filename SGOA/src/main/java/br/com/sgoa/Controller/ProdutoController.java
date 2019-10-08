/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Produto;
import br.com.sgoa.Enums.TipoProduto;
import br.com.sgoa.Facade.ProdutoFacade;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ProdutoController extends AbstractController<Produto> implements Serializable {

    @Inject
    private ProdutoFacade ejbFacade;

    @Getter
    @Setter
    private TipoProduto tipoProduto;

    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
       return Produto.class;
    }

    @Override
    protected String getAutoComplete() {
        return "nomeProduto";
    }

}
