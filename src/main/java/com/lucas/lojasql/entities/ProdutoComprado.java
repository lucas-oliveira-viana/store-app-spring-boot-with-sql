package com.lucas.lojasql.entities;

public class ProdutoComprado extends Produto{

	private Integer id;
	private Integer idCompra;
	private Integer idEstoque;
	private Integer quantidade;
	private Double valorTotalProduto;
	
	public ProdutoComprado(String nome, Integer idCompra, Integer idEstoque, Double valor, Integer codigoBarras, Integer quantidade) {
		super(nome, valor, codigoBarras);
		this.quantidade = quantidade;
		this.idCompra = idCompra;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(Integer idEstoque) {
		this.idEstoque = idEstoque;
	}

	public Integer getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(Integer idCompra) {
		this.idCompra = idCompra;
	}

	public Double getValorTotalProduto() {
		return valorTotalProduto;
	}     

	public void setValorTotalProduto(Double valorTotalProduto) {
		this.valorTotalProduto = valorTotalProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public Double calcularValorTotalCadaProduto(Integer quantidade, Double valor) {
		return valorTotalProduto = quantidade * valor;
	}
}
