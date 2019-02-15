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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.lojasql.controller.utils.Decoder;
import com.lucas.lojasql.controller.utils.FromDTO;
import com.lucas.lojasql.controller.utils.ToDTO;
import com.lucas.lojasql.dto.FuncionarioDTO;
import com.lucas.lojasql.entities.Funcionario;
import com.lucas.lojasql.service.FuncionarioService;

@RestController
@RequestMapping(value = "/loja/funcionario")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;
	
	@GetMapping(value = "/consulta")
	public ResponseEntity<List<FuncionarioDTO>> listarFuncionarios(){
		List<Funcionario> funcionarios = funcionarioService.findAll();
		List<FuncionarioDTO> dto = ToDTO.passarFuncionarioParaDTO(funcionarios);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/filtrarporcpf")
	public ResponseEntity<FuncionarioDTO> filtrarFuncionarioPorCpf(@RequestParam (value = "cpf", defaultValue = "") String cpf){
		cpf = Decoder.inserirPontuacoesCPF(cpf);
		Funcionario funcionariosQueContemEsseCpf = funcionarioService.findByCpf(cpf);
		FuncionarioDTO dto = new FuncionarioDTO(funcionariosQueContemEsseCpf);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping(value = "/cadastro")
	public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
		Funcionario funcionario = FromDTO.fromDTOFuncionario(funcionarioDTO);
		funcionarioService.insert(funcionario);
		return ResponseEntity.ok().body(funcionario);
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<String> deleteFuncionario(@PathVariable Integer id) {
		funcionarioService.deleteById(id);
		return ResponseEntity.ok().body("O Funcionario do id: " + id + " foi excluído");
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<String> updateFuncionario(@RequestBody FuncionarioDTO funcionarioDTO, @PathVariable Integer id){
		Funcionario funcionarioAtualizado = FromDTO.fromDTOFuncionario(funcionarioDTO);
		funcionarioAtualizado.setId(id);
		funcionarioService.update(funcionarioAtualizado);
		return ResponseEntity.ok().body("O cadastro de: " + funcionarioAtualizado.getNome() + " foi atualizado com sucesso!");
	}
}
