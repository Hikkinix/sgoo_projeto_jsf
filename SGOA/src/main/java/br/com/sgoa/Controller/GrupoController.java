/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Grupo;
import br.com.sgoa.Enums.TipoGrupo;
import br.com.sgoa.Enums.TipoProduto;
import br.com.sgoa.Facade.GrupoFacade;
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
public class GrupoController extends AbstractController<Grupo> implements Serializable {

    @Inject
    private GrupoFacade ejbFacade;

    public List<Grupo> listaGrupoPessoa() {
        return ejbFacade.listaFiltro("tipoGrupo", TipoGrupo.PESSOA.name());
    }
    
    public List<Grupo> listaGrupoProduto() {
        return ejbFacade.listaFiltro("tipoGrupo", TipoGrupo.PRODUTO.name());
    }

    public void novoGrupoProduto() {
        super.novo();
        super.getSelected().setTipoGrupo(TipoGrupo.PRODUTO);
    }

    public void novoGrupoPessoa() {
        super.novo();
        super.getSelected().setTipoGrupo(TipoGrupo.PESSOA);
    }

    public List<Grupo> listaFiltrandoProduto(String filtro) {
        return ejbFacade.listaFiltrandoProduto(filtro, getAutoComplete());
    }

    public List<Grupo> listaFiltrandoPessoa(String filtro) {
        return ejbFacade.listaFiltrandoPessoa(filtro, getAutoComplete());
    }

    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
        return Grupo.class;
    }

    @Override
    protected String getAutoComplete() {
        return "nomeGrupo";
    }

}
