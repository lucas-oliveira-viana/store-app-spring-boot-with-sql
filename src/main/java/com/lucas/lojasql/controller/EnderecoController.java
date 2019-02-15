package com.lucas.lojasql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.lojasql.controller.utils.FromDTO;
import com.lucas.lojasql.controller.utils.ToDTO;
import com.lucas.lojasql.dto.EnderecoDTO;
import com.lucas.lojasql.entities.Endereco;
import com.lucas.lojasql.service.EnderecoService;

@RestController
@RequestMapping(value = "/loja/endereco")
public class EnderecoController {

	@Autowired
	public EnderecoService enderecoService;
	
	@GetMapping(value = "/consulta")
	public ResponseEntity<List<EnderecoDTO>> listarEnderecos(){
		List<Endereco> enderecos = enderecoService.findAll();
		List<EnderecoDTO> dto = ToDTO.passarEnderecoParaDTO(enderecos);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/consultacep")
	public ResponseEntity<EnderecoDTO> listarEnderecosPeloCep(@RequestBody EnderecoDTO enderecoDTO){
		Endereco endereco = FromDTO.fromDTOEndereco(enderecoDTO);
		endereco = enderecoService.findByCep(enderecoDTO.getCep());
		return ResponseEntity.ok().body(new EnderecoDTO(endereco));
	}
	
	@PostMapping(value = "/cadastro")
	public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
		Endereco endereco = FromDTO.fromDTOEndereco(enderecoDTO);
		enderecoService.insert(endereco);
		return ResponseEntity.ok().body(endereco);
	}
}