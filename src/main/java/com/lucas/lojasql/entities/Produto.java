package com.lucas.lojasql.entities;

public abstract class Produto {

	private String nome;
	private Double valor;
	private String codigoBarras;
	
	public Produto(String nome, Double valor, String codigoBarras) {
		this.nome = nome;
		this.valor = valor;
		this.codigoBarras = codigoBarras;
	}

	public Produto() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
}
