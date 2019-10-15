/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Unidade;
import br.com.sgoa.Entidade.Usuario;
import br.com.sgoa.Facade.UnidadeFacade;
import br.com.sgoa.Facade.UsuarioFacade;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class UsuarioController extends AbstractController<Usuario> implements Serializable {

    @Inject
    private UsuarioFacade ejbFacade;

    @Override
    public void salvar() throws Exception {
        if (super.getSelected().getSenha().equals(super.getSelected().getComfirmarsenha())){
            super.salvar();
        } else {
            errorMensagem("Comfirmação de senha incorreta");
        }
    }
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
        return "login";
    }

}
