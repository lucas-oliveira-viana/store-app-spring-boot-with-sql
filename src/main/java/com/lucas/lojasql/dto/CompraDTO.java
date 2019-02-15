package com.lucas.lojasql.dto;

import java.util.List;

import com.lucas.lojasql.entities.ProdutoComprado;
import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.entities.Compra;
import com.lucas.lojasql.entities.Funcionario;

public class CompraDTO {

	private Integer id;
	private List<ProdutoComprado> produtosComprados;
	private Cliente cliente;
	private Funcionario funcionario;
	private String formaPagamento;
	private Double valorTotal;
	
	public CompraDTO() {
	}
	
	public CompraDTO(Compra compra) {
		id = compra.getId();
		produtosComprados = compra.getProdutosComprados();
		cliente = compra.getCliente();
		funcionario = compra.getFuncionario();
		valorTotal = compra.getValorTotal();
		formaPagamento = compra.getFormaPagamento();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<ProdutoComprado> getCesta() {
		return produtosComprados;
	}

	public void setProdutosComprados(List<ProdutoComprado> produto) {
		this.produtosComprados = produto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
}
