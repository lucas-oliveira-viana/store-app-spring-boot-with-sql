package com.lucas.lojasql.dto;

import com.lucas.lojasql.entities.Endereco;

public class EnderecoDTO {

	private Integer id;
	private String cep;
	private String pais;
	private String estado;
	private String cidade;
	private String bairro;
	private String rua;
	private String numero;
	
	public EnderecoDTO() {
	}
	
	public EnderecoDTO(Endereco endereco) {
		this.id = endereco.getId();
		this.cep = endereco.getCep();
		this.pais = endereco.getPais();
		this.estado = endereco.getEstado();
		this.cidade = endereco.getCidade();
		this.bairro = endereco.getBairro();
		this.rua = endereco.getRua();
		this.numero = endereco.getNumero();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
}
