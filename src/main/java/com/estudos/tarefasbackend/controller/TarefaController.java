package com.estudos.tarefasbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudos.tarefasbackend.domain.Tarefa;
import com.estudos.tarefasbackend.exception.ResourceNotFoundException;
import com.estudos.tarefasbackend.repository.TarefaRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TarefaController {
	
	@Autowired
	private TarefaRepository tarefaRepository;

	
	@GetMapping("/tarefas")
	public List<Tarefa> listarTodos() {
		return tarefaRepository.findAll();
	}
	
	@PostMapping("/tarefas")
	public Tarefa cadastrarTarefa(@RequestBody Tarefa tarefa) {

		/* Por padrão, a tarefa é sempre cadastrada como não concluída. */
		tarefa.setConcluida(false);
		return tarefaRepository.save(tarefa);
		
	}
	
	@GetMapping("/tarefas/{idTarefa}")
	public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable Long idTarefa) {
		
		Tarefa tarefa = tarefaRepository.findById(idTarefa)
				.orElseThrow(() -> new ResourceNotFoundException("[buscarTarefaPorId]: Não existe Tarefa com o id = " + idTarefa));
		return ResponseEntity.ok(tarefa);
		
	}
	
	@PutMapping("/tarefas/{idTarefa}")
	public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long idTarefa, @RequestBody Tarefa tarefaDetails) {
		
		Tarefa tarefa = tarefaRepository.findById(idTarefa)
				.orElseThrow(() -> new ResourceNotFoundException("[atualizarTarefa]: Não existe Tarefa com o id = " + idTarefa));
		
		tarefa.setNome(tarefaDetails.getNome());
		tarefa.setConcluida(tarefaDetails.getConcluida());
		
		Tarefa tarefaUpdate = tarefaRepository.save(tarefa);
		return ResponseEntity.ok(tarefaUpdate);
		
	}
	
	@DeleteMapping("/tarefas/{idTarefa}")
	public ResponseEntity<Map<String, Boolean>> deletarTarefa(@PathVariable Long idTarefa) {
		
		Tarefa tarefa = tarefaRepository.findById(idTarefa)
				.orElseThrow(() -> new ResourceNotFoundException("[deletarTarefa]: Não existe Tarefa com o id = " + idTarefa));
		
		tarefaRepository.delete(tarefa);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("deleted", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
		
	}
	
	@PutMapping("/tarefas/alterar-status/{idTarefa}")
	public ResponseEntity<Tarefa> alterarStatusTarefa(@PathVariable Long idTarefa, @RequestBody Tarefa tarefaDetails) {

		Tarefa tarefa = tarefaRepository.findById(idTarefa)
				.orElseThrow(() -> new ResourceNotFoundException("[alterarStatusTarefa]: Não existe Tarefa com o id = " + idTarefa));
		
		tarefa.setConcluida(tarefaDetails.getConcluida());
		
		Tarefa tarefaUpdate = tarefaRepository.save(tarefa);
		return ResponseEntity.ok(tarefaUpdate);
		
	}	
	
}
