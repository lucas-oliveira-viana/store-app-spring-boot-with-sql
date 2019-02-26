package com.lucas.lojasql.service;

import org.springframework.stereotype.Service;

import com.lucas.lojasql.entities.ProdutoComprado;
import com.lucas.lojasql.interfaces.CestaInterface;
import com.lucas.lojasql.interfaces.DaoFactory;

@Service
public class CestaService implements CestaInterface{

	@Override
	public void insert(ProdutoComprado produtoComprado) {
		DaoFactory.createCestaDao().insert(produtoComprado);
	}

	@Override
	public void deleteCestaByIdCompra(Integer id) {
		DaoFactory.createCestaDao().deleteCestaByIdCompra(id);
	}

}
