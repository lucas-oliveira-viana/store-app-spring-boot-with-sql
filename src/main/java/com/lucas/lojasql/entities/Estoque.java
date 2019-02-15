package com.lucas.lojasql.entities;

public class Estoque extends Produto {

	private Integer id;
	private Integer estoque;

	public Estoque(Integer id, String nome, Double valor, String codigoBarras, Integer estoque) {
		super(nome, valor, codigoBarras);
		this.id = id;
		this.estoque = estoque;
	}

	public Estoque() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
}