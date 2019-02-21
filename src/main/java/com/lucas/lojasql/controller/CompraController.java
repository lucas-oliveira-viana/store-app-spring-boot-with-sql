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
	public ResponseEntity<Compra> realizarCompra(@RequestBody CompraDTO compraDTO) {
		Compra compra = FromDTO.fromDTOCompra(compraDTO);
		compraService.insert(compra);
		return ResponseEntity.ok().body(compra);
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
}
