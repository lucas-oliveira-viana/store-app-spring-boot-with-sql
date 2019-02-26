package com.lucas.lojasql.controller;

import static com.lucas.lojasql.utils.ToDTO.passarCompraParaDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.lojasql.dto.CompraDTO;
import com.lucas.lojasql.entities.Compra;
import com.lucas.lojasql.entities.ProdutoComprado;
import com.lucas.lojasql.service.CompraService;
import com.lucas.lojasql.utils.FromDTO;
import com.lucas.lojasql.utils.URL;

@RestController
@RequestMapping(value = "/loja/compra")
public class CompraController {

	@Autowired
	private CompraService compraService;

	@GetMapping(value = "/consulta")
	public ResponseEntity<List<CompraDTO>> listarTodasCompras() {
		List<Compra> compras = compraService.findAll();
		List<CompraDTO> dto = passarCompraParaDTO(compras);
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/id/{id}")
	public ResponseEntity<CompraDTO> listaCompraById(@PathVariable Integer id) {
		Compra compra = compraService.findById(id);
		return ResponseEntity.ok().body(new CompraDTO(compra));
	}

	@GetMapping(value = "/filtrarporproduto")
	public ResponseEntity<List<CompraDTO>> filtrarComprasPorProdutoContido(@RequestParam(value = "produto", defaultValue = "") String produto) {
		produto = URL.decodeParam(produto);
		List<Compra> comprasQueContemEsseProduto = compraService.findByProdutoComprado(produto);
		List<CompraDTO> dto = passarCompraParaDTO(comprasQueContemEsseProduto);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping(value = "/comprar")
	public ResponseEntity<String> realizarCompra(@RequestBody CompraDTO compraDTO) {
		Compra compra = FromDTO.fromDTOCompra(compraDTO);
		compraService.insert(compra);
		return ResponseEntity.ok().body("Compra realizada com sucesso!\n" + toStringRelatorioCompra(compra));
	}

	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<String> deleteCompra(@PathVariable Integer id) {
		compraService.deleteById(id);
		return ResponseEntity.ok().body("A Compra do id: " + id + " foi excluída");
	}

	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<String> updateCompra(@RequestBody CompraDTO compraAtualizadaDTO, @PathVariable Integer id) {
		Compra compraAtualizada = FromDTO.fromDTOCompra(compraAtualizadaDTO);
		compraAtualizada.setId(id);
		compraService.update(compraAtualizada);
		return ResponseEntity.ok().body("A compra foi atualizada com sucesso!");
	}
	
	private String toStringRelatorioCompra(Compra compra) {
		String msg = "\nProdutos Comprados: \n" + toStringProdutosComprados(compra) + "\n" +
					 "Nome do Cliente: " + compra.getCliente().getNome() + "\n" +
					 "Nome do Funcionário: " + compra.getFuncionario().getNome() + "\n" +
					 "Forma de Pagamento: " + compra.getFormaPagamento() + "\n" +
					 "Valor Total: " + compra.getValorTotal();
		return msg;
	}
	
	private String toStringProdutosComprados(Compra compra) {
		String msg = "";
		for (ProdutoComprado produtoComprado : compra.getProdutosComprados()) {
			msg += "Produto: " + produtoComprado.getNome() + ", " + "Quantidade: " + produtoComprado.getQuantidade() + "\n";
		}
		return msg;
	}
}
