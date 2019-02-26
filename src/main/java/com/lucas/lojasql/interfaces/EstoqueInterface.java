package com.lucas.lojasql.interfaces;

import java.util.List;

import com.lucas.lojasql.entities.Estoque;
import com.lucas.lojasql.entities.ProdutoComprado;

public interface EstoqueInterface {

	List<Estoque> findAll();
	
	Estoque findByCodigoBarras(Integer codigoDeBarras);
	
	void insert(Estoque estoque);
	
	void update(Estoque estoque);
	
	void deleteByCodigoBarras(Integer codigoDeBarras);

	void modificarQuantidadeDoEstoqueDeUmProduto(Integer quantidade, Integer id);
	
	void retiraDoEstoque(ProdutoComprado produtoComprado);
	
	boolean verificaSeExisteNoEstoque(Integer codigoBarras);
}
