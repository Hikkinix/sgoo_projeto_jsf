package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Cidade;
import br.com.sgoa.Entidade.Parcelas;
import br.com.sgoa.util.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Transacional
public class ParcelasFacade extends AbstractFacade<Parcelas>{

    @Inject
    private EntityManager em;

    public ParcelasFacade() {
        super(Parcelas.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
