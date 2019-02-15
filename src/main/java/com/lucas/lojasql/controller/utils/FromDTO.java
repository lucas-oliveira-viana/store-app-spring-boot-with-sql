package com.lucas.lojasql.controller.utils;

import com.lucas.lojasql.dto.CompraDTO;
import com.lucas.lojasql.entities.Compra;
import com.lucas.lojasql.dto.ClienteDTO;
import com.lucas.lojasql.dto.EnderecoDTO;
import com.lucas.lojasql.dto.FuncionarioDTO;
import com.lucas.lojasql.dto.EstoqueDTO;
import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.entities.Funcionario;
import com.lucas.lojasql.entities.Estoque;

public class FromDTO {

	public static Compra fromDTOCompra(CompraDTO compraDTO) {
		return new Compra(compraDTO.getId(), compraDTO.getCesta(), compraDTO.getCliente(),
				compraDTO.getFuncionario(), compraDTO.getFormaPagamento());
	}
	public static Cliente fromDTOCliente(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getDataNascimento(),
				clienteDTO.getCpf(), clienteDTO.getRg(), clienteDTO.getEmail(), clienteDTO.getTelefone(),
				clienteDTO.getEndereco());
	}

	public static Endereco fromDTOEndereco(EnderecoDTO enderecoDTO) {
		return new Endereco(enderecoDTO.getCep(), enderecoDTO.getPais(), enderecoDTO.getEstado(),
				enderecoDTO.getCidade(), enderecoDTO.getBairro(), enderecoDTO.getRua(), enderecoDTO.getNumero());
	}
	
	public static Estoque fromDTOEstoque(EstoqueDTO ProdutoEmEstoqueDTO) {
		return new Estoque(ProdutoEmEstoqueDTO.getId(), ProdutoEmEstoqueDTO.getNome(),
				ProdutoEmEstoqueDTO.getValor(), ProdutoEmEstoqueDTO.getCodigoBarras(),
				ProdutoEmEstoqueDTO.getEstoque());
	}
	
	public static Funcionario fromDTOFuncionario(FuncionarioDTO funcionarioDTO) {
		return new Funcionario(funcionarioDTO.getId(), funcionarioDTO.getNome(), funcionarioDTO.getDataNascimento(),
				funcionarioDTO.getCpf(), funcionarioDTO.getRg(), funcionarioDTO.getEmail(),
				funcionarioDTO.getTelefone(), funcionarioDTO.getEndereco(), funcionarioDTO.getCargo());
	}
}