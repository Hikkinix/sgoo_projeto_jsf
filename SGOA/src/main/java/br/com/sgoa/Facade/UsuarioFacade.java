package br.com.sgoa.Facade;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Entidade.Banco;
import br.com.sgoa.Entidade.Usuario;
import br.com.sgoa.util.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Transacional
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @Inject
    private EntityManager em;

    public UsuarioFacade() {
        super(Usuario.class);
    }


    public Usuario salvar(Usuario objeto) throws Exception {
        if (objeto.getSenha().equals(objeto.getComfirmarsenha())) {
            return super.salvar(objeto);
        } else {
            throw new Exception("Erro: Senhas Não conhecidem");
        }

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    public Usuario validaUsuario(Usuario u) {/// verifica se existe usuario cadastrado no banco de dados
        String hql = "from Usuario obj where obj.login = :filtro1 and obj.senha = :filtro2";
        Query q = getEntityManager().createQuery(hql);
        q.setParameter("filtro1", u.getLogin());
        q.setParameter("filtro2", u.getSenha());
        if (q.getResultList().isEmpty()) {
            return u;
        } else {
            return (Usuario) q.getSingleResult();
        }

    }

}
