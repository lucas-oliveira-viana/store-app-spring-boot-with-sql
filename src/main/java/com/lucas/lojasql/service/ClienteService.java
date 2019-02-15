package com.lucas.lojasql.service;

import static com.lucas.lojasql.dao.DaoFactory.createClienteDao;
import static com.lucas.lojasql.dao.DaoFactory.createEnderecoDao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.lojasql.dao.ClienteInterface;
import com.lucas.lojasql.dao.DaoFactory;
import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.entities.Endereco;

@Service
public class ClienteService implements ClienteInterface {

	@Override
	public List<Cliente> findAll() {
		return createClienteDao().findAll();
	}

	@Override
	public Cliente findById(Integer id) {
		return createClienteDao().findById(id);
	}
	
	@Override
	public Cliente findByCpf(String cpf) {
		return createClienteDao().findByCpf(cpf);
	}

	@Override
	public void insert(Cliente cliente) {
		Endereco findByCep = createEnderecoDao().findByCep(cliente.getEndereco().getCep());

		if (findByCep != null) {
			cliente.getEndereco().setId(findByCep.getId());
		}
		createClienteDao().insert(cliente);
	}

	@Override
	public void update(Cliente cliente) {
		Endereco findByCep = createEnderecoDao().findByCep(cliente.getEndereco().getCep());

		if (findByCep != null) {
			cliente.getEndereco().setId(findByCep.getId());
		}
		createClienteDao().update(cliente);
	}

	@Override
	public void deleteById(Integer id) {
		DaoFactory.createClienteDao().deleteById(id);
	}

}
