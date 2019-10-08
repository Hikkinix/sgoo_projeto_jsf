/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Pessoa;
import br.com.sgoa.Entidade.Telefone;
import br.com.sgoa.Facade.TelefoneFacade;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@Named
@ViewScoped
public class TelefoneController extends AbstractController<Telefone> implements Serializable {

    @Inject
    private TelefoneFacade ejbFacade;

    @Getter @Setter
    private Telefone telefone;

    @Override
    public void novo() {
        telefone = new Telefone();
        super.layoutCreate();
    }

    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
       return Telefone.class;
    }

    @Override
    protected String getAutoComplete() {
        return "numero";
    }

    public void addTelefone(Pessoa pessoa) {
        pessoa.getTelefones().add(telefone);
        telefone.setPessoa(pessoa);
        telefone = new Telefone();
    }
    public void removeTelefone(Pessoa pessoa) {
        pessoa.getTelefones().remove(telefone);
    }

    public void editTelefone(Telefone tel){
        setTelefone(tel);
        System.out.println("teste");
    }
}
