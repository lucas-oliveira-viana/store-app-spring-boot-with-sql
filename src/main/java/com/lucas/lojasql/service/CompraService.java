package com.lucas.lojasql.service;

import static com.lucas.lojasql.interfaces.DaoFactory.createCestaDao;
import static com.lucas.lojasql.interfaces.DaoFactory.createCompraDao;
import static com.lucas.lojasql.interfaces.DaoFactory.createEstoqueDao;
import static com.lucas.lojasql.interfaces.DaoFactory.createFuncionarioDao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.entities.Compra;
import com.lucas.lojasql.entities.Estoque;
import com.lucas.lojasql.entities.Funcionario;
import com.lucas.lojasql.entities.ProdutoComprado;
import com.lucas.lojasql.exception.UserException;
import com.lucas.lojasql.exception.estoque.InsufficientEstoqueException;
import com.lucas.lojasql.exception.produto.ProdutoNotFoundException;
import com.lucas.lojasql.interfaces.CompraInterface;
import com.lucas.lojasql.interfaces.DaoFactory;
import com.lucas.lojasql.utils.Calculadora;

@Service
public class CompraService implements CompraInterface {

	@Override
	public List<Compra> findAll() {
		return createCompraDao().findAll();
	}

	@Override
	public Compra findById(Integer id) {
		return createCompraDao().findById(id);
	}

	@Override
	public List<Compra> findByProdutoComprado(String NomeDoProdutoComprado) {
		return createCompraDao().findByProdutoComprado(NomeDoProdutoComprado);
	}

	@Override
	public void insert(Compra compra) {
		
		setaValorTotalDaCompra(compra);
		
		String cpfCliente = compra.getCliente().getCPF();
		String cpfFuncionario = compra.getFuncionario().getCPF();
		List<ProdutoComprado> produtosComprados = compra.getProdutosComprados();

		Cliente findByCpfCliente = DaoFactory.createClienteDao().findByCpf(cpfCliente);
		Funcionario findByCpfFuncionario = createFuncionarioDao().findByCpf(cpfFuncionario);

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
		
		for (ProdutoComprado produtoComprado : produtosComprados) {
			if (verificaSeProdutoCompradoExisteNoEstoque(produtoComprado)) {
				retiraDoEstoque(produtoComprado);
			}
		}
		
		createCompraDao().insert(compra);
		
		setaValorDeCadaProduto(produtosComprados);

		insereCadaProdutoNaCesta(compra, produtosComprados);
	}

	@Override
	public void update(Compra compraAtualizada) {

		List<ProdutoComprado> produtosComprados = compraAtualizada.getProdutosComprados();

		setaValorDeCadaProduto(produtosComprados);

		setaValorTotalDaCompra(compraAtualizada);

		limpaCestaDessaCompra(compraAtualizada.getId());

		insereCadaProdutoNaCesta(compraAtualizada, produtosComprados);

		createCompraDao().update(compraAtualizada);
	}

	@Override
	public void deleteById(Integer id) {
		createCompraDao().deleteById(id);
	}

	public boolean verificaSeProdutoCompradoExisteNoEstoque(ProdutoComprado produtoComprado) {
		boolean existeProdutoNoEstoque = createEstoqueDao().findByCodigoBarras(produtoComprado.getCodigoBarras()) != null;
		if (existeProdutoNoEstoque) {
			return true;
		}
		throw new ProdutoNotFoundException("Produto não encontrado no estoque: " + produtoComprado);
	}

	public Estoque retiraDoEstoque(ProdutoComprado produtoComprado) {
		Estoque produtoCompradoNoEstoque = createEstoqueDao().findByCodigoBarras(produtoComprado.getCodigoBarras());

		Integer quantidadeDeEstoqueDoProdutoComprado = produtoCompradoNoEstoque.getEstoque();
		Integer quantidadeProdutoComprada = produtoComprado.getQuantidade();

		if (quantidadeDeEstoqueDoProdutoComprado < quantidadeProdutoComprada) {
			throw new InsufficientEstoqueException("Existem " + quantidadeDeEstoqueDoProdutoComprado
					+ " unidades" + " de " + produtoComprado.getNome() + " no estoque!");
		}
		Integer quantidadeEstoqueProdutoAtualizada = quantidadeDeEstoqueDoProdutoComprado - quantidadeProdutoComprada;
		
		createEstoqueDao().updateQuantidadeEstoqueDoProduto(quantidadeEstoqueProdutoAtualizada, produtoCompradoNoEstoque.getId());
		
		return produtoCompradoNoEstoque;
	}

	private void setaValorDeCadaProduto(List<ProdutoComprado> produtosComprados) {
		for (ProdutoComprado produtoComprado : produtosComprados) {
			produtoComprado.setValorTotalProduto(produtoComprado
					.calcularValorTotalCadaProduto(produtoComprado.getQuantidade(), produtoComprado.getValor()));
		}
	}

	private void setaValorTotalDaCompra(Compra compraAtualizada) {
		compraAtualizada.setValorTotal(Calculadora.definirValorTotal(compraAtualizada));
	}

	private void limpaCestaDessaCompra(Integer id) {
		createCestaDao().deleteById(id);
	}

	private void insereCadaProdutoNaCesta(Compra compra, List<ProdutoComprado> produtosComprados) {
		for (ProdutoComprado produtoComprado : produtosComprados) {
			
			Estoque findByCodigoBarras = createEstoqueDao().findByCodigoBarras(produtoComprado.getCodigoBarras());

			if (findByCodigoBarras != null) {
				produtoComprado.setIdCompra(compra.getId());
				produtoComprado.setIdEstoque(findByCodigoBarras.getId());

				createCestaDao().insert(produtoComprado);
			}
		}
	}
}
