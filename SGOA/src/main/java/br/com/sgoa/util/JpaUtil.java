/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ricardo
 */
public class JpaUtil {

    //Criando o entityManager, static final, cria uma vez só.
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("SystemServicePU");

    //fabrica de entity managed
    @Produces
    //O objeto do entityManager fica vivo na memoria somente durante a requisição.
    @RequestScoped
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //Disposes, ao fim da requisiÇão o objeto é fechado.
    public void close(@Disposes EntityManager manager) {
        manager.close();
    }
}
