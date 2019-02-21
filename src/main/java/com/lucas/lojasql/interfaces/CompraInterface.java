package com.lucas.lojasql.interfaces;

import java.util.List;

import com.lucas.lojasql.entities.Compra;

public interface CompraInterface {

	List<Compra> findAll();

	Compra findById(Integer id);

	List<Compra> findByProdutoComprado(String produtoComprado);

	void insert(Compra compra);

	void update(Compra compra);

	void deleteById(Integer id);
}
