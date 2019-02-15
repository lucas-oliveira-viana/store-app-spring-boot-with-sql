package com.lucas.lojasql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.lojasql.controller.utils.FromDTO;
import com.lucas.lojasql.dto.CompraDTO;
import com.lucas.lojasql.entities.Compra;
import com.lucas.lojasql.service.CompraService;

@RestController
@RequestMapping(value = "/loja/compra")
public class CompraController {

	@Autowired
	private CompraService compraService;
//
//	@GetMapping(value = "/consulta")
//	public ResponseEntity<List<CompraDTO>> listarTodasCompras() {
//		List<Compra> compras = compraService.findAllCompras();
//		List<CompraDTO> dto = passarCompraParaDTO(compras);
//		return ResponseEntity.ok().body(dto);
//	}
//
//	@GetMapping(value = "/id/{id}")
//	public ResponseEntity<CompraDTO> listaCompraById(@PathVariable String id) {
//		Compra compra = compraService.findComprasById(id);
//		return ResponseEntity.ok().body(new CompraDTO(compra));
//	}
//
//	@GetMapping(value = "/filtrarporproduto")
//	public ResponseEntity<List<CompraDTO>> filtrarComprasPorProdutoContido(@RequestParam(value = "produto", defaultValue = "") String produto) {
//		produto = URL.decodeParam(produto);
//		List<Compra> comprasQueContemEsseProduto = compraService.findByProdutosComprados(produto);
//		List<CompraDTO> dto = passarCompraParaDTO(comprasQueContemEsseProduto);
//		return ResponseEntity.ok().body(dto);
//	}
//
	@PostMapping(value = "/comprar")
	public ResponseEntity<Compra> realizarCompra(@RequestBody CompraDTO compraDTO) {
		Compra compra = FromDTO.fromDTOCompra(compraDTO);
		compraService.insert(compra);
		return ResponseEntity.ok().body(compra);
	}

//	@DeleteMapping(value = "/deletar/{id}")
//	public ResponseEntity<String> deleteCompra(@PathVariable String id) {
//		compraService.deleteCompra(id);
//		return ResponseEntity.ok().body("A Compra do id: " + id + " foi excluída");
//	}
//
//	@PutMapping(value = "/atualizar/{id}")
//	public ResponseEntity<String> updateCompra(@RequestBody CompraDTO compraAtualizadaDTO, @PathVariable String id) {
//		Compra compraAtualizada = FromDTO.fromDTOCompra(compraAtualizadaDTO);
//		compraAtualizada.setId(id);
//		compraService.updateCompra(compraAtualizada);
//		return ResponseEntity.ok().body("A compra de: " + compraAtualizada.getCliente().getNome() + " foi atualizada com sucesso!");
//	}
}
