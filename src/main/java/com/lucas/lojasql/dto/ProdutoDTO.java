package com.lucas.lojasql.dto;

import java.io.Serializable;

import com.lucas.lojasql.entities.Produto;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private Double valor;
	private Integer codigoBarras;

	public ProdutoDTO() {
	}

	public ProdutoDTO(Produto produto) {
		this.nome = produto.getNome();
		this.valor = produto.getValor();
		this.codigoBarras = produto.getCodigoBarras();
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
