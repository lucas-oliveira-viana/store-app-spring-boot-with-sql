package com.lucas.lojasql.dao;

import java.util.List;

import com.lucas.lojasql.entities.Compra;

public interface CompraInterface {

	List<Compra> findAll();

	Compra findById(String id);

	Compra findByProdutoComprado(String produtoComprado);

	void insert(Compra compra);

	void update(Compra compra);

	void deleteById(String id);
}
