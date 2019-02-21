package com.lucas.lojasql.service;

import static com.lucas.lojasql.interfaces.DaoFactory.createEnderecoDao;
import static com.lucas.lojasql.interfaces.DaoFactory.createFuncionarioDao;
import static com.lucas.lojasql.interfaces.DaoFactory.inicializaValidacoes;
import static com.lucas.lojasql.validator.TipoDocumento.CEP;
import static com.lucas.lojasql.validator.TipoDocumento.CPF;
import static com.lucas.lojasql.validator.TipoDocumento.EMAIL;
import static com.lucas.lojasql.validator.TipoDocumento.RG;
import static com.lucas.lojasql.validator.TipoUsuario.FUNCIONARIO;
import static com.lucas.lojasql.validator.ValidatorDocumento.validarDocumento;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.entities.Funcionario;
import com.lucas.lojasql.interfaces.FuncionarioInterface;

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

		fluxoDeValidacoesFuncionario(funcionario);

		Endereco findByCep = createEnderecoDao().findByCep(funcionario.getEndereco().getCep());

		if (findByCep != null) {
			funcionario.getEndereco().setId(findByCep.getId());
		}
		createFuncionarioDao().insert(funcionario);
	}


	@Override
	public void update(Funcionario funcionario) {
		
		fluxoDeValidacoesFuncionario(funcionario);
		
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

	@Override
	public Funcionario findByRg(String rg) {
		return createFuncionarioDao().findByRg(rg);
	}

	@Override
	public Funcionario findByEmail(String email) {
		return createFuncionarioDao().findByEmail(email);
	}
	
	private void fluxoDeValidacoesFuncionario(Funcionario funcionario) {
		inicializaValidacoes().verificaSeCampoJaEstaCadastrado(RG, FUNCIONARIO);
		inicializaValidacoes().verificaSeCampoJaEstaCadastrado(CPF, FUNCIONARIO);
		inicializaValidacoes().verificaSeCampoJaEstaCadastrado(EMAIL, FUNCIONARIO);
		
		validarDocumento(funcionario.getCPF(), CPF);
		validarDocumento(funcionario.getRG(), RG);
		validarDocumento(funcionario.getEndereco().getCep(), CEP);
	}
}
