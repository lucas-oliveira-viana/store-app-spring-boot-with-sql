package com.lucas.lojasql.service;

import static com.lucas.lojasql.interfaces.DaoFactory.createClienteDao;
import static com.lucas.lojasql.interfaces.DaoFactory.createEnderecoDao;
import static com.lucas.lojasql.interfaces.DaoFactory.inicializaValidacoes;
import static com.lucas.lojasql.validator.TipoDocumento.CEP;
import static com.lucas.lojasql.validator.TipoDocumento.CPF;
import static com.lucas.lojasql.validator.TipoDocumento.EMAIL;
import static com.lucas.lojasql.validator.TipoDocumento.RG;
import static com.lucas.lojasql.validator.TipoUsuario.CLIENTE;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.interfaces.ClienteInterface;
import com.lucas.lojasql.interfaces.DaoFactory;
import com.lucas.lojasql.validator.ValidatorDocumento;

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
		
		fluxoDeValidacoesCliente(cliente);
		
		Endereco findByCep = createEnderecoDao().findByCep(cliente.getEndereco().getCep());

		if (findByCep != null) {
			cliente.getEndereco().setId(findByCep.getId());
		}
		createClienteDao().insert(cliente);
	}


	@Override
	public void update(Cliente cliente) {
		
		fluxoDeValidacoesCliente(cliente);
		
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

	@Override
	public Cliente findByRg(String rg) {
		return createClienteDao().findByRg(rg);
	}

	@Override
	public Cliente findByEmail(String email) {
		return createClienteDao().findByEmail(email);
	}

	private void fluxoDeValidacoesCliente(Cliente cliente) {
		inicializaValidacoes().verificaSeCampoJaEstaCadastrado(RG, CLIENTE);
		inicializaValidacoes().verificaSeCampoJaEstaCadastrado(CPF, CLIENTE);
		inicializaValidacoes().verificaSeCampoJaEstaCadastrado(EMAIL, CLIENTE);
		
		ValidatorDocumento.validarDocumento(cliente.getCPF(), CPF);
		ValidatorDocumento.validarDocumento(cliente.getRG(), RG);
		ValidatorDocumento.validarDocumento(cliente.getEndereco().getCep(), CEP);
	}
	
}
