package com.lucas.lojasql.interfaces;

import com.lucas.lojasql.jdbc.DB;
import com.lucas.lojasql.repository.CestaRepository;
import com.lucas.lojasql.repository.ClienteRepository;
import com.lucas.lojasql.repository.CompraRepository;
import com.lucas.lojasql.repository.EnderecoRepository;
import com.lucas.lojasql.repository.EstoqueRepository;
import com.lucas.lojasql.repository.FuncionarioRepository;
import com.lucas.lojasql.validator.ValidatorDocumentoBanco;
import com.lucas.lojasql.validator.ValidatorDocumentoBancoInterface;

public class DaoFactory {

	public static ClienteInterface createClienteDao() {
		return new ClienteRepository(DB.getConnection());
	}
	
	public static EnderecoInterface createEnderecoDao() {
		return new EnderecoRepository(DB.getConnection());
	}
	
	public static FuncionarioInterface createFuncionarioDao() {
		return new FuncionarioRepository(DB.getConnection());
	}
	
	public static EstoqueInterface createEstoqueDao() {
		return new EstoqueRepository(DB.getConnection());
	}
	
	public static CompraInterface createCompraDao() {
		return new CompraRepository(DB.getConnection());
	}
	
	public static CestaInterface createCestaDao() {
		return new CestaRepository(DB.getConnection());
	}
	
	public static ValidatorDocumentoBancoInterface inicializaValidacoes() {
		return new ValidatorDocumentoBanco();
	}
}