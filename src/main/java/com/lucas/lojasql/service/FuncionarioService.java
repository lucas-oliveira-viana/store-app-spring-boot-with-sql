package com.lucas.lojasql.service;

import static com.lucas.lojasql.dao.DaoFactory.createEnderecoDao;
import static com.lucas.lojasql.dao.DaoFactory.createFuncionarioDao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.lojasql.dao.FuncionarioInterface;
import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.entities.Funcionario;

@Service
public class FuncionarioService implements FuncionarioInterface {

	@Override
	public List<Funcionario> findAll() {
		return createFuncionarioDao().findAll();
	}

	@Override
	public Funcionario findById(Integer id) {
		return createFuncionarioDao().findById(id);
	}

	@Override
	public Funcionario findByCpf(String cpf) {
		return createFuncionarioDao().findByCpf(cpf);
	}

	@Override
	public void insert(Funcionario funcionario) {
		Endereco findByCep = createEnderecoDao().findByCep(funcionario.getEndereco().getCep());

		if (findByCep != null) {
			funcionario.getEndereco().setId(findByCep.getId());
		}
		createFuncionarioDao().insert(funcionario);
	}

	@Override
	public void update(Funcionario funcionario) {
		Endereco findByCep = createEnderecoDao().findByCep(funcionario.getEndereco().getCep());

		if (findByCep != null) {
			funcionario.getEndereco().setId(findByCep.getId());
		}
		createFuncionarioDao().update(funcionario);
	}

	@Override
	public void deleteById(Integer id) {
		createFuncionarioDao().deleteById(id);
	}
}
