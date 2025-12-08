package com.ProjetoExtensao.Projeto.controllers;

import com.ProjetoExtensao.Projeto.models.Vacina;
import com.ProjetoExtensao.Projeto.servicos.VacinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacinas")
@RequiredArgsConstructor
public class VacinaController {

    private final VacinaService vacinaService;

    @PostMapping
    public ResponseEntity<String> cadastrarVacina(
            @RequestParam String pacienteCpf,
            @RequestParam String nomeVacina,
            @RequestParam String dataAplicacao
    ) {
        vacinaService.salvarVacina(pacienteCpf, nomeVacina, dataAplicacao);
        return ResponseEntity.ok("Vacina registrada com sucesso!");
    }

    @GetMapping("/paciente/{cpf}")
    public ResponseEntity<List<Vacina>> listarPorPacienteCpf(@PathVariable String cpf) {
        List<Vacina> vacinas = vacinaService.findVacinasByPacienteCpf(cpf);
        return ResponseEntity.ok(vacinas);
    }

    @GetMapping("/paciente/id/{id}")
    public ResponseEntity<List<Vacina>> listarPorPacienteId(@PathVariable Long id) {
        List<Vacina> vacinas = vacinaService.findVacinasByPacienteId(id);
        return ResponseEntity.ok(vacinas);
    }
}