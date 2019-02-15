package com.lucas.lojasql.entities;

import java.util.Date;

public class Cliente extends Pessoa {

	private Integer id;
	
	public Cliente(Integer id, String nome, Date dataNascimento, String cpf, String rg, String email, String telefone,
			Endereco endereco) {
		super(nome, dataNascimento, cpf, rg, email, telefone, endereco);
		this.id = id;
	}

	public Cliente() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
