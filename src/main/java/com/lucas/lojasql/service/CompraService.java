package com.lucas.lojasql.service;

import static com.lucas.lojasql.interfaces.DaoFactory.createCestaDao;
import static com.lucas.lojasql.interfaces.DaoFactory.createClienteDao;
import static com.lucas.lojasql.interfaces.DaoFactory.createCompraDao;
import static com.lucas.lojasql.interfaces.DaoFactory.createEstoqueDao;
import static com.lucas.lojasql.interfaces.DaoFactory.createFuncionarioDao;
import static com.lucas.lojasql.utils.Calculadora.setaValorDeCadaProdutoDaCesta;
import static com.lucas.lojasql.utils.Calculadora.setaValorTotalDaCompra;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.entities.Compra;
import com.lucas.lojasql.entities.Estoque;
import com.lucas.lojasql.entities.Funcionario;
import com.lucas.lojasql.entities.ProdutoComprado;
import com.lucas.lojasql.exception.UserException;
import com.lucas.lojasql.exception.produto.ProdutoNotFoundException;
import com.lucas.lojasql.interfaces.CompraInterface;

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


		String cpfCliente = compra.getCliente().getCPF();
		String cpfFuncionario = compra.getFuncionario().getCPF();
		List<ProdutoComprado> produtosComprados = compra.getProdutosComprados();

		Cliente findByCpfCliente = createClienteDao().findByCpf(cpfCliente);
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

		retiraVariosProdutosDoEstoque(produtosComprados);
		
		setaValorTotalDaCompra(compra);

		createCompraDao().insert(compra);

		setaValorDeCadaProdutoDaCesta(produtosComprados);

		insereCadaProdutoNaCesta(compra, produtosComprados);
	}

	@Override
	public void update(Compra compraAtualizada) {

		List<ProdutoComprado> produtosComprados = compraAtualizada.getProdutosComprados();

		setaValorDeCadaProdutoDaCesta(produtosComprados);

		setaValorTotalDaCompra(compraAtualizada);

		limpaCesta(compraAtualizada);

		insereCadaProdutoNaCesta(compraAtualizada, produtosComprados);

		createCompraDao().update(compraAtualizada);
	}

	@Override
	public void deleteById(Integer id) {
		createCompraDao().deleteById(id);
	}

	private static void insereCadaProdutoNaCesta(Compra compra, List<ProdutoComprado> produtosComprados) {
		for (ProdutoComprado produtoComprado : produtosComprados) {

			Estoque findByCodigoBarras = createEstoqueDao().findByCodigoBarras(produtoComprado.getCodigoBarras());

			if (findByCodigoBarras != null) {
				produtoComprado.setIdCompra(compra.getId());
				produtoComprado.setIdEstoque(findByCodigoBarras.getId());

				createCestaDao().insert(produtoComprado);
			}
		}
	}

	private void retiraVariosProdutosDoEstoque(List<ProdutoComprado> produtosComprados) {
		for (ProdutoComprado produtoComprado : produtosComprados) {
			retiraDoEstoque(produtoComprado);
		}
	}

	private void retiraDoEstoque(ProdutoComprado produtoComprado) {
		if (verificaSeExisteNoEstoque(produtoComprado.getCodigoBarras())) {
			createEstoqueDao().retiraDoEstoque(produtoComprado);
		} else {
			throw new ProdutoNotFoundException("Produto " + produtoComprado.getNome() + " não existe no estoque");
		}
	}

	private boolean verificaSeExisteNoEstoque(Integer codigoBarras) {
		if (createEstoqueDao().verificaSeExisteNoEstoque(codigoBarras)) {
			return true;
		}
		return false;
	}

	private void limpaCesta(Compra compraAtualizada) {
		createCestaDao().deleteCestaByIdCompra(compraAtualizada.getId());
	}

}
