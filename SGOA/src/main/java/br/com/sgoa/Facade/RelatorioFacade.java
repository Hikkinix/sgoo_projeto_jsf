package br.com.sgoa.Facade;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

/**
 *
 * @author Matheus
 */
public class RelatorioFacade implements Serializable {

    @Inject
    private EntityManager em;

    private static Connection con = null;

    public static Connection getConnection() {
        if (con == null) {
            try {
                String driver = "org.postgresql.Driver";
                String url = "jdbc:postgresql://localhost:5432/databaseSGO";
                String usuario = "postgres";
                String senha = "postgres";
                Class.forName(driver);
                con = DriverManager.getConnection(url, usuario, senha);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return con;
    }

}
