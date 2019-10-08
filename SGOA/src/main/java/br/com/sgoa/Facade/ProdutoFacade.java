package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Produto;
import br.com.sgoa.util.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Transacional
public class ProdutoFacade extends AbstractFacade<Produto>{

    @Inject
    private EntityManager em;

    public ProdutoFacade() {
        super(Produto.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
