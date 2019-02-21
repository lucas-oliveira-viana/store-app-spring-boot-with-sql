package com.lucas.lojasql.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.lucas.lojasql.dto.ClienteDTO;
import com.lucas.lojasql.dto.CompraDTO;
import com.lucas.lojasql.dto.EnderecoDTO;
import com.lucas.lojasql.dto.EstoqueDTO;
import com.lucas.lojasql.dto.FuncionarioDTO;
import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.entities.Compra;
import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.entities.Estoque;
import com.lucas.lojasql.entities.Funcionario;

public class ToDTO {

	public static List<FuncionarioDTO> passarFuncionarioParaDTO(List<Funcionario> listaFuncionarios) {
		return listaFuncionarios.stream().map(funcionario -> new FuncionarioDTO(funcionario)).collect(Collectors.toList());
	}
	
	public static List<ClienteDTO> passarClienteParaDTO(List<Cliente> clientes) {
		return clientes.stream().map(cliente -> new ClienteDTO(cliente)).collect(Collectors.toList());
	}
	
	public static List<CompraDTO> passarCompraParaDTO(List<Compra> comprasQueContemEsseProduto) {
		return comprasQueContemEsseProduto.stream().map(compra -> new CompraDTO(compra)).collect(Collectors.toList());
	}
	
	public static List<EnderecoDTO> passarEnderecoParaDTO(List<Endereco> enderecos) {
		return enderecos.stream().map(endereco -> new EnderecoDTO(endereco)).collect(Collectors.toList());
	}
	
	public static List<EstoqueDTO> passarEstoqueParaDTO(List<Estoque> produtoEmEstoque) {
		return produtoEmEstoque.stream().map(produto -> new EstoqueDTO(produto))
				.collect(Collectors.toList());
	}
}
