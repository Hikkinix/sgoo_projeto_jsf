package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Banco;
import br.com.sgoa.util.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Transacional
public class BancoFacade extends AbstractFacade<Banco>{

    @Inject
    private EntityManager em;

    public BancoFacade() {
        super(Banco.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
