package com.lucas.lojasql.service;

import static com.lucas.lojasql.interfaces.DaoFactory.createEstoqueDao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.lojasql.entities.Estoque;
import com.lucas.lojasql.entities.ProdutoComprado;
import com.lucas.lojasql.interfaces.EstoqueInterface;

@Service
public class EstoqueService implements EstoqueInterface{

	@Override
	public List<Estoque> findAll() {
		return createEstoqueDao().findAll();
	}

	@Override
	public Estoque findByCodigoBarras(Integer codigoDeBarras) {
		return createEstoqueDao().findByCodigoBarras(codigoDeBarras);
	}

	@Override
	public void insert(Estoque estoque) {
		createEstoqueDao().insert(estoque);
	}
	
	@Override
	public void update(Estoque estoque) {
		createEstoqueDao().update(estoque);
	}
	
	@Override
	public void deleteByCodigoBarras(Integer codigoDeBarras) {
		createEstoqueDao().deleteByCodigoBarras(codigoDeBarras);
	}

	@Override
	public void modificarQuantidadeDoEstoqueDeUmProduto(Integer quantidade, Integer id) {
		createEstoqueDao().modificarQuantidadeDoEstoqueDeUmProduto(quantidade, id);
	}

	@Override
	public void retiraDoEstoque(ProdutoComprado produtoComprado) {
		createEstoqueDao().retiraDoEstoque(produtoComprado);
	}

	@Override
	public boolean verificaSeExisteNoEstoque(Integer codigoBarras) {
		return createEstoqueDao().verificaSeExisteNoEstoque(codigoBarras);
	}
}
