package br.edu.icomp.ufam.bancoDeDados;

import java.sql.*;

public class BancoDeDados {
  private static String url = "jdbc:mysql://localhost:3306/db_financas";
  private static String user = "root";
  private static String pass = "Jv!07670206";
  protected static Connection conexao = null;

  public BancoDeDados() {
	  if (conexao == null) conecta();
  }

  public static String conecta() {
    try {
    	conexao = DriverManager.getConnection(url, user, pass);
      	return "Sucesso ao conectar ao banco";
    } catch (SQLException e) { 
    	return "Falha ao conectar ao banco"; 
    }
  }

  public static String desconecta() {
	  try {
		  conexao.close();
		  return "Sucesso ao desconectar do banco";
	  } catch (SQLException e) { 
		  return "Falha ao desconectar"; 
	  }
  }
}

