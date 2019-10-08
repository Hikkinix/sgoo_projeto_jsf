package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Cidade;
import br.com.sgoa.Entidade.Estado;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import br.com.sgoa.util.Transacional;

@Transacional
public class CidadeFacade extends AbstractFacade<Cidade>{

    @Inject
    private EntityManager em;

    public CidadeFacade() {
        super(Cidade.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
