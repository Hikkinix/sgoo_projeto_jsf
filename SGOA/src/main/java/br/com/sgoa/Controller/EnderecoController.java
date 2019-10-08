/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Endereco;
import br.com.sgoa.Entidade.Estado;
import br.com.sgoa.Entidade.Pessoa;
import br.com.sgoa.Facade.EnderecoFacade;
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
public class EnderecoController extends AbstractController<Endereco> implements Serializable {

    @Inject
    private EnderecoFacade ejbFacade;

    @Getter
    @Setter
    private Endereco endereco;

    @Override
    public void novo() {
            endereco = new Endereco();
            super.layoutCreate();
    }

    public void addEndereco(Pessoa pessoa) {
        pessoa.getEnderecos().add(endereco);
        endereco.setPessoa(pessoa);
        endereco = new Endereco();
    }
    public void removeEndereco(Pessoa pessoa) {
        pessoa.getEnderecos().remove(endereco);
    }

    public void editEndereco(Endereco end){
        endereco = end;
        System.out.println("teste");
    }

    @Override
    public void salvar(){
        layoutList();
        ejbFacade.salvar(endereco);
    }


    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
       return Endereco.class;
    }

    @Override
    protected String getAutoComplete() {
        return "rua";
    }

}
