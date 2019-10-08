/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sgoa.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author jaime
 */
public class Conexao implements Serializable{

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

	public static void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
