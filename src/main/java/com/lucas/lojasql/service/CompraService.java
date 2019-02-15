package com.lucas.lojasql.service;

import static com.lucas.lojasql.dao.DaoFactory.createCompraDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.lojasql.controller.utils.Calculadora;
import com.lucas.lojasql.dao.CompraInterface;
import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.entities.Compra;
import com.lucas.lojasql.entities.Estoque;
import com.lucas.lojasql.entities.Funcionario;
import com.lucas.lojasql.entities.ProdutoComprado;
import com.lucas.lojasql.exception.UserException;

@Service
public class CompraService implements CompraInterface {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EstoqueService estoqueService;

	@Autowired
	private CestaService cestaService;

	@Override
	public List<Compra> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Compra findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Compra findByProdutoComprado(String produtoComprado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Compra compra) {
		
		compra.setValorTotal(Calculadora.definirValorTotal(compra));
		
		String cpfCliente = compra.getCliente().getCPF();
		String cpfFuncionario = compra.getFuncionario().getCPF();
		List<ProdutoComprado> produtosComprados = compra.getProdutosComprados();

		Cliente findByCpfCliente = clienteService.findByCpf(cpfCliente);
		Funcionario findByCpfFuncionario = funcionarioService.findByCpf(cpfFuncionario);

		if (findByCpfCliente != null) {
			compra.getCliente().setId(findByCpfCliente.getId());
		} else {
			throw new UserException("Cliente não existe");
		}
		if (findByCpfFuncionario != null) {
			compra.getFuncionario().setId(findByCpfFuncionario.getId());
		} else {
			throw new UserException("Funcionario não existe");
		}
		
		createCompraDao().insert(compra);

		for (ProdutoComprado produtoComprado : produtosComprados) {
			Estoque findByCodigoBarras = estoqueService.findByCodigoBarras(produtoComprado.getCodigoBarras());
			
			produtoComprado.setValorTotalProduto(produtoComprado.calcularValorTotalCadaProduto(produtoComprado.getQuantidade(), produtoComprado.getValor()));

			if (findByCodigoBarras != null) {
				produtoComprado.setIdCompra(compra.getId());
				produtoComprado.setIdEstoque(findByCodigoBarras.getId());

				cestaService.insert(produtoComprado);
			}
		}
	}

	@Override
	public void update(Compra compra) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub

	}

}
