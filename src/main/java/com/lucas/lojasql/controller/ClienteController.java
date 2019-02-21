package com.lucas.lojasql.controller;

import static com.lucas.lojasql.utils.FromDTO.fromDTOCliente;
import static com.lucas.lojasql.utils.ToDTO.passarClienteParaDTO;

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

import com.lucas.lojasql.dto.ClienteDTO;
import com.lucas.lojasql.entities.Cliente;
import com.lucas.lojasql.service.ClienteService;
import com.lucas.lojasql.utils.Decoder;

@RestController
@RequestMapping(value = "/loja/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	
	@GetMapping(value = "/consulta")
	public ResponseEntity<List<ClienteDTO>> listarClientes(){
		List<Cliente> clientes = clienteService.findAll();
		List<ClienteDTO> dto = passarClienteParaDTO(clientes);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/filtrarporcpf")
	public ResponseEntity<ClienteDTO> filtrarClientePorCpf(@RequestParam (value = "cpf", defaultValue = "") String cpf){
		cpf = Decoder.inserirPontuacoesCPF(cpf);
		Cliente clientesQueContemEsseCpf = clienteService.findByCpf(cpf);
		ClienteDTO dto = new ClienteDTO(clientesQueContemEsseCpf);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping(value = "/cadastro")
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
		Cliente cliente = fromDTOCliente(clienteDTO);
		clienteService.insert(cliente);
		return ResponseEntity.ok().body(cliente);
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<String> deleteCliente(@PathVariable Integer id) {
		clienteService.deleteById(id);
		return ResponseEntity.ok().body("O Cliente do id: " + id + " foi excluído");
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<String> updateCliente(@RequestBody ClienteDTO clienteDTO, @PathVariable Integer id){
		Cliente clienteAtualizado = fromDTOCliente(clienteDTO);
		clienteAtualizado.setId(id);
		clienteService.update(clienteAtualizado);
		return ResponseEntity.ok().body("O cadastro de: " + clienteAtualizado.getNome() + " foi atualizado com sucesso!");
	}
}
