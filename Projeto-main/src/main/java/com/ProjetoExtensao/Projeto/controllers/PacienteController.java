package com.ProjetoExtensao.Projeto.controllers;

import com.ProjetoExtensao.Projeto.models.Paciente;
import com.ProjetoExtensao.Projeto.servicos.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> criarPaciente(@RequestBody Paciente paciente) {
        pacienteService.salvarPaciente(paciente);
        return ResponseEntity.ok(paciente);
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Paciente>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(pacienteService.findPacientesByNomeCompleto(nome));
    }

    @GetMapping("/buscar/cpf")
    public ResponseEntity<List<Paciente>> buscarPorCpf(@RequestParam String cpf) {
        return ResponseEntity.ok(pacienteService.findPacientesByCpf(cpf));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Paciente> buscarPorCpfExato(@PathVariable String cpf) {
        return ResponseEntity.ok(pacienteService.findPacienteByCpf(cpf));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        return ResponseEntity.ok(pacienteService.findAllPacientes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        pacienteService.deletarPacientePorId(id);
        return ResponseEntity.ok("Paciente deletado com sucesso.");
    }
}