package com.lucas.lojasql.exception.funcionario;

public class FuncionarioAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FuncionarioAlreadyExistsException(String msg) {
		super(msg);
	}
}
