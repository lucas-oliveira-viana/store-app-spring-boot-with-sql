package com.lucas.lojasql.dto;

import com.lucas.lojasql.entities.Estoque;

public class EstoqueDTO{
	
	private Integer id;
	private String nome;
	private Double valor;
	private Integer codigoBarras;
	private Integer estoque;
	
	public EstoqueDTO() {
	}
	
	public EstoqueDTO(Estoque estoqueProduto) {
		this.id = estoqueProduto.getId();
		this.nome = estoqueProduto.getNome();
		this.valor = estoqueProduto.getValor();
		this.codigoBarras = estoqueProduto.getCodigoBarras();
		this.estoque = estoqueProduto.getEstoque();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
}