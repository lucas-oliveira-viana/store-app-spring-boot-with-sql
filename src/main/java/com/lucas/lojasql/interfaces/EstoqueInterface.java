package com.lucas.lojasql.interfaces;

import java.util.List;

import com.lucas.lojasql.entities.Estoque;

public interface EstoqueInterface {

	List<Estoque> findAll();
	
	Estoque findByCodigoBarras(Integer codigoDeBarras);
	
	void insert(Estoque estoque);
	
	void update(Estoque estoque);
	
	void deleteByCodigoBarras(Integer codigoDeBarras);

	void updateQuantidadeEstoqueDoProduto(Integer quantidade, Integer id);
}
