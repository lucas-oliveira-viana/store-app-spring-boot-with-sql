package com.lucas.lojasql.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.lucas.lojasql.entities.Compra;
import com.lucas.lojasql.entities.ProdutoComprado;

public class Calculadora {

	public static Double definirValorTotal(Compra compra) {
		double somaValorDeTodosOsProdutos = listValorDeCadaProdutoComprado(compra)
																				.stream()
																				.mapToDouble(valorProduto -> valorProduto)
																				.sum();
		return somaValorDeTodosOsProdutos;
	}

	public static List<Double> listValorDeCadaProdutoComprado(Compra compra) {
		return compra
				.getProdutosComprados()
				.stream()
				.map(produto -> produto.getQuantidade() * produto.getValor())
				.collect(Collectors.toList());
	}
	
	public static void setaValorDeCadaProdutoDaCesta(List<ProdutoComprado> produtosComprados) {
		for (ProdutoComprado produtoComprado : produtosComprados) {
			produtoComprado.setValorTotalProduto(produtoComprado
					.calcularValorTotalCadaProduto(produtoComprado.getQuantidade(), produtoComprado.getValor()));
		}
	}

	public static void setaValorTotalDaCompra(Compra compraAtualizada) {
		compraAtualizada.setValorTotal(Calculadora.definirValorTotal(compraAtualizada));
	}
}
