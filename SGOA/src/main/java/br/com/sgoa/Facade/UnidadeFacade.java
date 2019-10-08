package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Marca;
import br.com.sgoa.Entidade.Unidade;
import br.com.sgoa.util.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Transacional
public class UnidadeFacade extends AbstractFacade<Unidade>{

    @Inject
    private EntityManager em;

    public UnidadeFacade() {
        super(Unidade.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
