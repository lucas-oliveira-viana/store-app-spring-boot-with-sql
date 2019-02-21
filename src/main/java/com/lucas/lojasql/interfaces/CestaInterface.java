package com.lucas.lojasql.interfaces;

import com.lucas.lojasql.entities.ProdutoComprado;

public interface CestaInterface {

	void insert(ProdutoComprado produtoComprado);
	
	void deleteById(Integer id);
}
