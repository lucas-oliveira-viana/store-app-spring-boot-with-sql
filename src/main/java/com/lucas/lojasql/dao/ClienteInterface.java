package com.lucas.lojasql.dao;

import java.util.List;

import com.lucas.lojasql.entities.Cliente;

public interface ClienteInterface {

	List<Cliente> findAll();
	
	Cliente findById(Integer id);
	
	Cliente findByCpf(String cpf);
	
	void insert(Cliente cliente);
	
	void update(Cliente cliente);
	
	void deleteById(Integer id);
}
