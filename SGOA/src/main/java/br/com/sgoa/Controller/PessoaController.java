/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Controller;

import br.com.sgoa.Abstract.AbstractController;
import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Endereco;
import br.com.sgoa.Entidade.Pessoa;
import br.com.sgoa.Entidade.Telefone;
import br.com.sgoa.Enums.Genero;
import br.com.sgoa.Facade.EnderecoFacade;
import br.com.sgoa.Facade.GrupoFacade;
import br.com.sgoa.Facade.PessoaFacade;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named(value = "pessoaController")
@ViewScoped
public class PessoaController extends AbstractController<Pessoa> implements Serializable {

    @Inject
    private PessoaFacade ejbFacade;
    @Inject
    private EnderecoFacade enderecoFacade;
    @Inject
    private GrupoFacade grupoFacade;
    @Inject
    private EnderecoController enderecoController;
    @Inject
    private TelefoneController telefoneController;

    @Override
    public void novo() {
        enderecoController.novo();
        telefoneController.novo();
        super.novo();
    }

    public Telefone getTelefone(){
        return telefoneController.getTelefone();
    }

    public Endereco getEndereco(){
        return enderecoController.getEndereco();
    }


    @Override
    public List<Pessoa> listaTodos() {
        return ejbFacade.listaTodos();
    }

    @Override
    protected AbstractFacade getEjb() {
        return ejbFacade;
    }

    @Override
    protected Class getClasse() {
       return Pessoa.class;
    }

    @Override
    protected String getAutoComplete() {
        return "nomePessoa";
    }

}
