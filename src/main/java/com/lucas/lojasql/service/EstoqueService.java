package com.lucas.lojasql.service;

import static com.lucas.lojasql.dao.DaoFactory.createEstoqueDao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.lojasql.dao.EstoqueInterface;
import com.lucas.lojasql.entities.Estoque;

@Service
public class EstoqueService implements EstoqueInterface{

	@Override
	public List<Estoque> findAll() {
		return createEstoqueDao().findAll();
	}

	@Override
	public Estoque findByCodigoBarras(String codigoDeBarras) {
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
	public void deleteByCodigoBarras(String codigoDeBarras) {
		createEstoqueDao().deleteByCodigoBarras(codigoDeBarras);
	}
}
