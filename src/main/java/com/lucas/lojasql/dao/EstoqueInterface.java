package com.lucas.lojasql.dao;

import java.util.List;

import com.lucas.lojasql.entities.Estoque;

public interface EstoqueInterface {

	List<Estoque> findAll();
	
	Estoque findByCodigoBarras(String codigoDeBarras);
	
	void insert(Estoque estoque);
	
	void update(Estoque estoque);
	
	void deleteByCodigoBarras(String codigoDeBarras);
}
