package br.com.sgoa.Abstract;

import br.com.sgoa.util.Transacional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Transacional
public abstract class AbstractFacade<T>  implements Serializable{
    
    private final Class<T> entityClass;
    
    protected abstract EntityManager getEntityManager();
    
    public AbstractFacade(Class<T> entityClass){
        this.entityClass = entityClass;
    }
    
    public T buscar(Long id){
        return getEntityManager().find(entityClass, id);
    }

    public T salvar(T objeto) throws Exception {
        return getEntityManager().merge(objeto);
    }
    
    public void excluir(T objeto){
        getEntityManager().remove(getEntityManager().merge(objeto));
    }
    
    public List<T> listaTodos(){
        Query q = getEntityManager().createQuery("from "+entityClass.getSimpleName());
        return q.getResultList();
    }

    public List<T> listaFiltrando(String filtro, String... atributos) {
        String hql = "from " + entityClass.getSimpleName() + " obj where ";
        for (String atributo : atributos) {
            hql += "lower(obj." + atributo + ") like :filtro OR ";
        }
        hql = hql.substring(0, hql.length() - 3);
        Query q = getEntityManager().createQuery(hql);
        q.setParameter("filtro", "%" + filtro.toLowerCase() + "%");

        return q.getResultList();
    }

    public List<String> listAutoComplete(String filtro, String campo, String... atributos) {
        String hql = "  from " + entityClass.getSimpleName() + " obj where ";
        for (String atributo : atributos) {
            hql += "lower(obj." + atributo + ") like :filtro OR ";
        }
        hql = hql.substring(0, hql.length() - 3);
        Query q = getEntityManager().createQuery(hql);
        q.setParameter("filtro", "%" + filtro.toLowerCase() + "%");

        return q.getResultList();
    }

    public List<T> findRange(int[] range) {
        Query q=getEntityManager().createQuery("from "+entityClass.getSimpleName());
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        Query q=getEntityManager().createQuery("from "+entityClass.getSimpleName());
        return q.getResultList().size();
    }

    public List<T> listaFiltro(String filtro, String atributo) {
        String hql = "from " + entityClass.getSimpleName() + " where "+ filtro +" = '"+ atributo +"'";
        Query q=getEntityManager().createQuery(hql);
        return q.getResultList()!=null? q.getResultList():new ArrayList<T>();
    }
}
