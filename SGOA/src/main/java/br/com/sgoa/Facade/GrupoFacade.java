package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Estado;
import br.com.sgoa.Entidade.Grupo;
import br.com.sgoa.Entidade.Telefone;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.sgoa.Enums.TipoGrupo;
import br.com.sgoa.util.Transacional;

import java.util.List;

@Transacional
public class GrupoFacade extends AbstractFacade<Grupo>{

    @Inject
    private EntityManager em;

    public List<Grupo> listaFiltrandoProduto(String filtro, String... atributos) {
        String hql = "from Grupo obj where obj.tipoGrupo = '" + TipoGrupo.PRODUTO +"' and ";
        for (String atributo : atributos) {
            hql += "lower(obj." + atributo + ") like :filtro OR ";
        }
        hql = hql.substring(0, hql.length() - 3);
        Query q = getEntityManager().createQuery(hql);
        q.setParameter("filtro", "%" + filtro.toLowerCase() + "%");

        return q.getResultList();
    }

    public List<Grupo> listaFiltrandoPessoa(String filtro, String... atributos) {
        String hql = "from Grupo obj where tipoGrupo = '" + TipoGrupo.PESSOA +"' and ";
        for (String atributo : atributos) {
            hql += "lower(obj." + atributo + ") like :filtro OR ";
        }
        hql = hql.substring(0, hql.length() - 3);
        Query q = getEntityManager().createQuery(hql);
        q.setParameter("filtro", "%" + filtro.toLowerCase() + "%");

        return q.getResultList();
    }

    public GrupoFacade() {
        super(Grupo.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
