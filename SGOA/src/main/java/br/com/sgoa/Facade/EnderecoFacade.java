package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Endereco;
import br.com.sgoa.Entidade.Estado;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.sgoa.Entidade.Telefone;
import br.com.sgoa.util.Transacional;

@Transacional
public class EnderecoFacade extends AbstractFacade<Endereco>{

    @Inject
    private EntityManager em;

    public Endereco salvar(Endereco objeto) {
       return em.merge(objeto);
    }

    public EnderecoFacade() {
        super(Endereco.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
