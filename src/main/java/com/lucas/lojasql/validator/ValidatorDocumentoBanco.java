package com.lucas.lojasql.validator;

import static com.lucas.lojasql.interfaces.DaoFactory.createClienteDao;
import static com.lucas.lojasql.interfaces.DaoFactory.createFuncionarioDao;
import static com.lucas.lojasql.validator.TipoDocumento.CPF;
import static com.lucas.lojasql.validator.TipoDocumento.EMAIL;
import static com.lucas.lojasql.validator.TipoDocumento.RG;
import static com.lucas.lojasql.validator.TipoUsuario.CLIENTE;
import static com.lucas.lojasql.validator.TipoUsuario.FUNCIONARIO;

import com.lucas.lojasql.exception.DocumentException;
import com.lucas.lojasql.exception.cliente.ClienteAlreadyExistsException;
import com.lucas.lojasql.exception.funcionario.FuncionarioAlreadyExistsException;

public class ValidatorDocumentoBanco implements ValidatorDocumentoBancoInterface{
	
	public ValidatorDocumentoBanco() {
	}

	public void verificaSeCampoJaEstaCadastrado(String documento, String tipoDeUsuario) {
		switch (tipoDeUsuario) {
		case FUNCIONARIO:
			switch (documento) {
			case CPF:
				if (createFuncionarioDao().findByCpf(documento) != null) {
					throw new DocumentException(mensagemErroAlreadyExists(FUNCIONARIO, CPF));
				}
			case RG:
				if (createFuncionarioDao().findByRg(documento) != null) {
					throw new DocumentException(mensagemErroAlreadyExists(FUNCIONARIO, RG));
				}
			case EMAIL:
				if (createFuncionarioDao().findByEmail(documento) != null) {
					throw new FuncionarioAlreadyExistsException(mensagemErroAlreadyExists(FUNCIONARIO, EMAIL));
				}
			}
		case CLIENTE:
			switch (documento) {
			case CPF:
				if (createClienteDao().findByCpf(documento) != null) {
					throw new DocumentException(mensagemErroAlreadyExists(CLIENTE, CPF));
				}
			case RG:
				if (createClienteDao().findByRg(documento) != null) {
					throw new DocumentException(mensagemErroAlreadyExists(CLIENTE, RG));
				}
			case EMAIL:
				if (createClienteDao().findByEmail(documento) != null) {
					throw new ClienteAlreadyExistsException(mensagemErroAlreadyExists(CLIENTE, EMAIL));
				}
			}
		}
	}

	private String mensagemErroAlreadyExists(String tipoUsuario, String documento) {
		return "Já existe um " + tipoUsuario + " com esse " + documento;
	}
}
