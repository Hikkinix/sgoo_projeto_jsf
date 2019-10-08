/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Cidade;
import br.com.sgoa.Entidade.Estado;
import br.com.sgoa.Facade.CidadeFacade;
import br.com.sgoa.Facade.EstadoFacade;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@ViewScoped
public class CidadeController extends AbstractController<Cidade> implements Serializable {

    @Inject
    private CidadeFacade ejbFacade;

    @Inject
    private EstadoFacade estadoFacade;

    @Getter @Setter private Boolean ativarEstado = false;

    public void prepareCreateEstado(){
        super.setAtivarCreate(false);
        ativarEstado = true;
    }

    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
       return Cidade.class;
    }

    @Override
    protected String getAutoComplete() {
        return "nomeCidade";
    }

    public List<Estado> getListaEstadoFiltrando(String filtro){
        return estadoFacade.listaFiltrando(filtro, "nomeEstado");
    }
}
