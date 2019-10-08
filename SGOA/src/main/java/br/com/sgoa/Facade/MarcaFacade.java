package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Marca;
import br.com.sgoa.util.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Transacional
public class MarcaFacade extends AbstractFacade<Marca>{

    @Inject
    private EntityManager em;

    public MarcaFacade() {
        super(Marca.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
