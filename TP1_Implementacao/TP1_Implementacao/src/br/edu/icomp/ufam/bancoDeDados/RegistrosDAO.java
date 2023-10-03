package br.edu.icomp.ufam.bancoDeDados;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.edu.icomp.ufam.tp1_implementacao.Cadastro;

public class RegistrosDAO extends BancoDeDados {
	
	public List<Cadastro> listarRendimentos() {
        List<Cadastro> listaDeRegistros = new ArrayList<>();
        try {
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, financas, data, categorias FROM financas");
            while (rs.next()) {
            	int id = rs.getInt("id");
                double rendimentos = rs.getDouble("financas");
                String data = rs.getString("data");
                String categoria = rs.getString("categorias");
                
                Cadastro registro = new Cadastro(id, rendimentos, data, categoria);
                listaDeRegistros.add(registro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDeRegistros;
    }
	
	public List<Cadastro> listarRendimentosPositivos() {
        List<Cadastro> listaDeRegistros = new ArrayList<>();
        try {
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM financas WHERE financas >= 0");
            while (rs.next()) {
            	int id = rs.getInt("id");
                double rendimentos = rs.getDouble("financas");
                String data = rs.getString("data");
                String categoria = rs.getString("categorias");
                
                Cadastro registro = new Cadastro(id, rendimentos, data, categoria);
                listaDeRegistros.add(registro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDeRegistros;
    }
	
	public List<Cadastro> listarRendimentosNegativos() {
        List<Cadastro> listaDeRegistros = new ArrayList<>();
        try {
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM financas WHERE financas < 0");
            while (rs.next()) {
            	int id = rs.getInt("id");
                double rendimentos = rs.getDouble("financas");
                String data = rs.getString("data");
                String categoria = rs.getString("categorias");
                
                Cadastro registro = new Cadastro(id, rendimentos, data, categoria);
                listaDeRegistros.add(registro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDeRegistros;
    }

	public boolean adicionarRegistro(Cadastro p) {
	    try {
	        String sql = "INSERT INTO financas (financas, data, categorias) VALUES (?, ?, ?)";
	        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
	        preparedStatement.setDouble(1, p.getRendimentos());
	        preparedStatement.setString(2, p.getData());
	        preparedStatement.setString(3, p.getCategoria());

	        int rowsAffected = preparedStatement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public double saldoUsuario() {
	    try {
	        Statement st = conexao.createStatement();
	        ResultSet rs = st.executeQuery("SELECT SUM(financas) FROM financas");
	        
	        if (rs.next()) {
	            return rs.getDouble(1);
	        } else {
	            return 0.0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 0.0;
	    }
	}
	
	public boolean limparRegistros() {
	    try {
	        String sql = "DELETE FROM financas";
	        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
	        
	        int registrosExcluidos = preparedStatement.executeUpdate();
	        
	        String sqlRedefinirSequencia = "ALTER TABLE financas AUTO_INCREMENT = 1";
	        PreparedStatement preparedStatementRedefinir = conexao.prepareStatement(sqlRedefinirSequencia);
	        preparedStatementRedefinir.executeUpdate();
	        
	        if (registrosExcluidos > 0) {
	            return true;
	        } else {
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean atualizarRegistroNoBanco(List<Cadastro> registros) {
	    PreparedStatement preparedStatement = null;
	    try {
	        conexao.setAutoCommit(false);

	        for (Cadastro registro : registros) {
	            preparedStatement = conexao.prepareStatement(
	                "UPDATE financas SET financas = ?, data = ?, categorias = ? WHERE id = ?"
	            );
	            preparedStatement.setDouble(1, registro.getRendimentos());
	            preparedStatement.setString(2, registro.getData());
	            preparedStatement.setString(3, registro.getCategoria());
	            preparedStatement.setInt(4, registro.getID());

	            int rowsUpdated = preparedStatement.executeUpdate();

	            if (rowsUpdated <= 0) {
	                conexao.rollback(); // Faz rollback
	                return false; // Se uma atualização falhar, retorna false imediatamente
	            }
	        }
	        conexao.commit(); // Se todas as atualizações forem bem-sucedidas, faz commit
	        return true; // Retorna true apenas se todas as atualizações foram bem-sucedidas
	    } catch (SQLException e) {
	        try {
	            if (conexao != null) {
	                conexao.rollback();
	            }
	        } catch (SQLException rollbackException) {
	            rollbackException.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            conexao.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false; // Retorna false em caso de exceção
	}





}
