package com.lucas.lojasql.utils.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.entities.Funcionario;

public class MapeamentoColunasSelectCompra {

	public static Cliente instanciaCliente(ResultSet rs) throws SQLException {
		return new Cliente(rs.getInt(PosicaoDasColunas.COLUNA_ID_CLIENTE),
						   rs.getString(PosicaoDasColunas.COLUNA_NOME_CLIENTE),
						   rs.getDate(PosicaoDasColunas.COLUNA_DATA_NASCIMENTO_CLIENTE),
						   rs.getString(PosicaoDasColunas.COLUNA_CPF_CLIENTE),
						   rs.getString(PosicaoDasColunas.COLUNA_RG_CLIENTE), 
						   rs.getString(PosicaoDasColunas.COLUNA_EMAIL_CLIENTE), 
						   rs.getString(PosicaoDasColunas.COLUNA_TELEFONE_CLIENTE),
						   instanciaEnderecoCliente(rs));
	}
	
	public static Funcionario instanciaFuncionario(ResultSet rs) throws SQLException {
		return new Funcionario(rs.getInt(PosicaoDasColunas.COLUNA_ID_FUNCIONARIO),
							   rs.getString(PosicaoDasColunas.COLUNA_NOME_FUNCIONARIO),
							   rs.getDate(PosicaoDasColunas.COLUNA_DATA_NASCIMENTO_FUNCIONARIO),
							   rs.getString(PosicaoDasColunas.COLUNA_CPF_FUNCIONARIO),
							   rs.getString(PosicaoDasColunas.COLUNA_RG_FUNCIONARIO),
							   rs.getString(PosicaoDasColunas.COLUNA_EMAIL_FUNCIONARIO),
							   rs.getString(PosicaoDasColunas.COLUNA_TELEFONE_FUNCIONARIO),
							   instanciaEnderecoFuncionario(rs),
							   rs.getString(PosicaoDasColunas.COLUNA_CARGO_FUNCIONARIO));
	}
	
	private static Endereco instanciaEnderecoCliente(ResultSet rs) throws SQLException {
		return new Endereco(rs.getString(PosicaoDasColunas.COLUNA_CEP_CLIENTE),
							rs.getString(PosicaoDasColunas.COLUNA_PAIS_CLIENTE),
							rs.getString(PosicaoDasColunas.COLUNA_ESTADO_CLIENTE),
							rs.getString(PosicaoDasColunas.COLUNA_CIDADE_CLIENTE),
							rs.getString(PosicaoDasColunas.COLUNA_BAIRRO_CLIENTE),
							rs.getString(PosicaoDasColunas.COLUNA_RUA_CLIENTE),
							rs.getString(PosicaoDasColunas.COLUNA_NUMERO_CLIENTE));
	}
	
	private static Endereco instanciaEnderecoFuncionario(ResultSet rs) throws SQLException {
		return new Endereco(rs.getString(PosicaoDasColunas.COLUNA_CEP_FUNCIONARIO),
							rs.getString(PosicaoDasColunas.COLUNA_PAIS_FUNCIONARIO),
							rs.getString(PosicaoDasColunas.COLUNA_ESTADO_FUNCIONARIO),
							rs.getString(PosicaoDasColunas.COLUNA_CIDADE_FUNCIONARIO),
							rs.getString(PosicaoDasColunas.COLUNA_BAIRRO_FUNCIONARIO),
							rs.getString(PosicaoDasColunas.COLUNA_RUA_FUNCIONARIO),
							rs.getString(PosicaoDasColunas.COLUNA_NUMERO_FUNCIONARIO));
	}
}
