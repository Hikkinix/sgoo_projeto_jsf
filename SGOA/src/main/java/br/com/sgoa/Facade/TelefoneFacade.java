package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Estado;
import br.com.sgoa.Entidade.Telefone;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import br.com.sgoa.util.Transacional;

@Transacional
public class TelefoneFacade extends AbstractFacade<Telefone>{

    @Inject
    private EntityManager em;

    public TelefoneFacade() {
        super(Telefone.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
