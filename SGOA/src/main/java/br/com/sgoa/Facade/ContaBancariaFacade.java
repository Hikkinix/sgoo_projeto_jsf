package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Banco;
import br.com.sgoa.Entidade.ContaBancaria;
import br.com.sgoa.util.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Transacional
public class ContaBancariaFacade extends AbstractFacade<ContaBancaria>{

    @Inject
    private EntityManager em;

    public ContaBancariaFacade() {
        super(ContaBancaria.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
