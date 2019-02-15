package com.lucas.lojasql.dao;

import com.lucas.lojasql.dao.impl.CestaRepository;
import com.lucas.lojasql.dao.impl.ClienteRepository;
import com.lucas.lojasql.dao.impl.CompraRepository;
import com.lucas.lojasql.dao.impl.EnderecoRepository;
import com.lucas.lojasql.dao.impl.EstoqueRepository;
import com.lucas.lojasql.dao.impl.FuncionarioRepository;
import com.lucas.lojasql.database.DB;

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
}