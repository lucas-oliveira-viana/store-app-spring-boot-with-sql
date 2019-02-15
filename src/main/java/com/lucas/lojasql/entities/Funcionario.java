package com.lucas.lojasql.entities;

import java.util.Date;

public class Funcionario extends Pessoa{

	private Integer id;
	private String cargo;

	public Funcionario(Integer id, String nome, Date dataNascimento, String cpf, String rg, String email, String telefone,
			Endereco endereco, String cargo) {
		super(nome, dataNascimento, cpf, rg, email, telefone, endereco);
		this.id = id;
		this.cargo = cargo;
	}
	
	public Funcionario() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
}
