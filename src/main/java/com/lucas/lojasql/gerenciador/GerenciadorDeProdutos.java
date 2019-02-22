package com.lucas.lojasql.gerenciador;

import static com.lucas.lojasql.interfaces.DaoFactory.createCestaDao;
import static com.lucas.lojasql.interfaces.DaoFactory.createEstoqueDao;

import java.util.List;

import com.lucas.lojasql.entities.Compra;
import com.lucas.lojasql.entities.Estoque;
import com.lucas.lojasql.entities.ProdutoComprado;
import com.lucas.lojasql.exception.estoque.InsufficientEstoqueException;
import com.lucas.lojasql.exception.produto.ProdutoNotFoundException;

public class GerenciadorDeProdutos {
	
	public static boolean verificaSeProdutoCompradoExisteNoEstoque(ProdutoComprado produtoComprado) {
		
		boolean existeProdutoNoEstoque = createEstoqueDao().findByCodigoBarras(produtoComprado.getCodigoBarras()) != null;
		
		if (existeProdutoNoEstoque) {
			return true;
		}
		throw new ProdutoNotFoundException("Produto não encontrado no estoque: " + produtoComprado);
	}
	
	public static void insereCadaProdutoNaCesta(Compra compra, List<ProdutoComprado> produtosComprados) {
		for (ProdutoComprado produtoComprado : produtosComprados) {
			
			Estoque findByCodigoBarras = createEstoqueDao().findByCodigoBarras(produtoComprado.getCodigoBarras());

			if (findByCodigoBarras != null) {
				produtoComprado.setIdCompra(compra.getId());
				produtoComprado.setIdEstoque(findByCodigoBarras.getId());

				createCestaDao().insert(produtoComprado);
			}
		}
	}

	public static Estoque retiraDoEstoque(ProdutoComprado produtoComprado) {
		
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
	
	public static void retiraProdutosDoEstoque(List<ProdutoComprado> produtosComprados) {
		for (ProdutoComprado produtoComprado : produtosComprados) {
			if (verificaSeProdutoCompradoExisteNoEstoque(produtoComprado)) {
				retiraDoEstoque(produtoComprado);
			}
		}
	}
}
