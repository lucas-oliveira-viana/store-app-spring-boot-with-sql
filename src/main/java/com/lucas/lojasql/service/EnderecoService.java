package com.lucas.lojasql.service;

import static com.lucas.lojasql.dao.DaoFactory.createEnderecoDao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.lojasql.dao.DaoFactory;
import com.lucas.lojasql.dao.EnderecoInterface;
import com.lucas.lojasql.entities.Endereco;

@Service
public class EnderecoService implements EnderecoInterface{

	@Override
	public List<Endereco> findAll() {
		return DaoFactory.createEnderecoDao().findAll();
	}

	@Override
	public Endereco findById(String id) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}
}
