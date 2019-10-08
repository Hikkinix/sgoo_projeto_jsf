package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.sgoa.util.Transacional;

import java.util.List;

@Transacional
public class PessoaFacade extends AbstractFacade<Pessoa>{

    @Inject
    private EntityManager em;

    public PessoaFacade() {
        super(Pessoa.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Telefone> carregarTelefone(Pessoa objeto){
        Query q = getEntityManager().createQuery("from  Telefone as t where t.pessoa = "+ objeto.getIdPessoa().toString());
        objeto.setTelefones(q.getResultList());
        return objeto.getTelefones();
    }

    public List<Endereco> carregarEndereco(Pessoa objeto){
        Query q = getEntityManager().createQuery("from  Endereco as t where t.pessoa = "+ objeto.getIdPessoa().toString());
        objeto.setEnderecos(q.getResultList());
        return objeto.getEnderecos();
    }

}
