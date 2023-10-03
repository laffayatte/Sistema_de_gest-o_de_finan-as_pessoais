package br.edu.icomp.ufam.tp1_implementacao;

import java.util.Date;

import br.edu.icomp.ufam.bancoDeDados.RegistrosDAO;

interface Registro {
    void setRendimentos(double rendimentos);
    void setData(String data);
    void setCategoria(String categoria);

    double getRendimentos();
    String getData();
    String getCategoria();
}

public class Cadastro extends RegistrosDAO {
	private int id;
	public double rendimentos;
	public String data;
	public String categoria;
	
	public Cadastro(int id, double rendimentos, String data, String categoria) {
		this.id = id;
		this.rendimentos = rendimentos;
		this.data = data;
		this.categoria = categoria;
	}
	
	public Cadastro() {
		this.rendimentos = 0.0;
		this.data = "20-06-2001";
		this.categoria = "default";
	}

	public void setRendimentos(double rendimentos) {
		this.rendimentos = rendimentos;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return this.id;
	}
	
	public double getRendimentos() {
		return this.rendimentos;
	}
	public String getData() {
		return this.data;
	}
	public String getCategoria() {
		return this.categoria;
	}
}
