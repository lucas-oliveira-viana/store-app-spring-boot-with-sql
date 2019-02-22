package com.lucas.lojasql.service;

import static com.lucas.lojasql.interfaces.DaoFactory.createEnderecoDao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.interfaces.DaoFactory;
import com.lucas.lojasql.interfaces.EnderecoInterface;

@Service
public class EnderecoService implements EnderecoInterface{

	@Override
	public List<Endereco> findAll() {
		return DaoFactory.createEnderecoDao().findAll();
	}

	@Override
	public Endereco findById(Integer id) {
		return createEnderecoDao().findById(id);
	}

	@Override
	public Endereco findByCep(String cep) {
		return createEnderecoDao().findByCep(cep);
	}
	
	@Override
	public void insert(Endereco endereco) {
		createEnderecoDao().insert(endereco);
	}

	@Override
	public void update(Endereco endereco) {
		createEnderecoDao().update(endereco);
	}

	@Override
	public void deleteById(Integer id) {
		createEnderecoDao().deleteById(id);
	}
}
