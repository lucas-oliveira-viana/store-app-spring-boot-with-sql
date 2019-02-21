package com.lucas.lojasql.entities;

public abstract class Produto {

	private String nome;
	private Double valor;
	private Integer codigoBarras;
	
	public Produto(String nome, Double valor, Integer codigoBarras) {
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

	public Integer getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(Integer codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
}
