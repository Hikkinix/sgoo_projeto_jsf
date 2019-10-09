package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Marca;
import br.com.sgoa.Entidade.MovimentaFinanceiro;
import br.com.sgoa.util.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Transacional
public class MovimentoFinaceiroFacade extends AbstractFacade<MovimentaFinanceiro>{

    @Inject
    private EntityManager em;

    public MovimentoFinaceiroFacade() {
        super(MovimentaFinanceiro.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
