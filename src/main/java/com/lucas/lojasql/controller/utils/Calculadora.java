package com.lucas.lojasql.controller.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.lucas.lojasql.entities.Compra;

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
}
