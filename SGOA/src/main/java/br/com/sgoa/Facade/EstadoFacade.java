package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Estado;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import br.com.sgoa.util.Transacional;

@Transacional
public class EstadoFacade extends AbstractFacade<Estado>{

    @Inject
    private EntityManager em;

    public EstadoFacade() {
        super(Estado.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
