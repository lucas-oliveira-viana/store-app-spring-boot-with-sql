package com.lucas.lojasql.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.lucas.lojasql.dto.EstoqueDTO;
import com.lucas.lojasql.entities.Estoque;
import com.lucas.lojasql.service.EstoqueService;
import com.lucas.lojasql.utils.FromDTO;
import com.lucas.lojasql.utils.ToDTO;

@RestController
@RequestMapping(value = "/loja/estoque")
public class EstoqueController {

	@Autowired
	private EstoqueService estoqueService;

	@GetMapping(value = "/consulta")
	public ResponseEntity<List<EstoqueDTO>> listarTodoEstoque() {
		
		List<Estoque> produtoEmEstoque = estoqueService.findAll();
		List<EstoqueDTO> dto = ToDTO.passarEstoqueParaDTO(produtoEmEstoque);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping(value = "/cadastro")
	public ResponseEntity<Estoque> cadastrarProduto(@RequestBody EstoqueDTO produtoParaSerCadastradoDTO) {
		
		Estoque produtoParaSerCadastrado = FromDTO.fromDTOEstoque(produtoParaSerCadastradoDTO);
		
		estoqueService.insert(produtoParaSerCadastrado);
		return ResponseEntity.ok().body(produtoParaSerCadastrado);
	}
	
	@DeleteMapping(value = "/deletar/{codigoBarras}")
	public ResponseEntity<String> deleteProduto(@PathVariable Integer codigoBarras) {
		estoqueService.deleteByCodigoBarras(codigoBarras);
		return ResponseEntity.ok().body("O Produto do id: " + codigoBarras + " foi excluído");
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<String> updateProduto(@RequestBody EstoqueDTO produtoEmEstoqueDTO,
			@PathVariable Integer id) {
		Estoque produtoEmEstoqueAtualizado = FromDTO.fromDTOEstoque(produtoEmEstoqueDTO);
		produtoEmEstoqueAtualizado.setId(id);
		estoqueService.update(produtoEmEstoqueAtualizado);
		return ResponseEntity.ok().body("O produto: " + produtoEmEstoqueAtualizado.getNome() + " foi atualizado com sucesso!");
	}
}
