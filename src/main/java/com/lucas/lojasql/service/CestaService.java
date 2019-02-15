package com.lucas.lojasql.service;

import org.springframework.stereotype.Service;

import com.lucas.lojasql.dao.CestaInterface;
import com.lucas.lojasql.dao.DaoFactory;
import com.lucas.lojasql.entities.ProdutoComprado;

@Service
public class CestaService implements CestaInterface{

	@Override
	public void insert(ProdutoComprado produtoComprado) {
		DaoFactory.createCestaDao().insert(produtoComprado);
	}

}
