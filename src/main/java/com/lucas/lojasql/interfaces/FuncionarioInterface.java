package com.lucas.lojasql.interfaces;

import java.util.List;

import com.lucas.lojasql.entities.Funcionario;

public interface FuncionarioInterface {

	List<Funcionario> findAll();
	
	Funcionario findById(Integer id);
	
	Funcionario findByCpf(String cpf);
	
	Funcionario findByRg(String rg);
	
	Funcionario findByEmail(String email);
	
	void insert(Funcionario cliente);
	
	void update(Funcionario cliente);
	
	void deleteById(Integer id);
}
