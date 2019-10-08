package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Estado;
import br.com.sgoa.Entidade.FormaPagamento;
import br.com.sgoa.Enums.TipoPlanoPagamento;
import br.com.sgoa.util.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Transacional
public class PlanoPagamentoFacade extends AbstractFacade<FormaPagamento>{

    @Inject
    private EntityManager em;

    public PlanoPagamentoFacade() {
        super(FormaPagamento.class);
    }

    public List<FormaPagamento> listaFiltrandoPlano(TipoPlanoPagamento tipo, String filtro, String... atributos) {
        String hql = "from FormaPagamento obj where tipoPlano = '"+ tipo +"'";
        for (String atributo : atributos) {
            hql += "lower(obj." + atributo + ") like :filtro OR ";
        }
        hql = hql.substring(0, hql.length() - 3);
        Query q = getEntityManager().createQuery(hql);
        q.setParameter("filtro", "%" + filtro.toLowerCase() + "%");

        return q.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
