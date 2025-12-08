package com.ProjetoExtensao.Projeto.controllers;

import com.ProjetoExtensao.Projeto.models.Prescricao;
import com.ProjetoExtensao.Projeto.servicos.PrescricaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescricoes")
@RequiredArgsConstructor
public class PrescricaoController {

    private final PrescricaoService prescricaoService;

    @GetMapping("/paciente/id/{pacienteId}")
    public ResponseEntity<List<Prescricao>> getPrescricoesByPacienteId(@PathVariable Long pacienteId) {
        List<Prescricao> prescricoes = prescricaoService.findPrescricoesByPacienteId(pacienteId);
        return ResponseEntity.ok(prescricoes);
    }

    @GetMapping("/paciente/cpf/{cpf}")
    public ResponseEntity<List<Prescricao>> getPrescricoesByPacienteCpf(@PathVariable String cpf) {
        List<Prescricao> prescricoes = prescricaoService.findPrescricoesByPacienteCpf(cpf);
        return ResponseEntity.ok(prescricoes);
    }

    @PostMapping
    public ResponseEntity<String> criarPrescricao(
            @RequestParam String pacienteCpf,
            @RequestParam String medicamento,
            @RequestParam String dosagem,
            @RequestParam String frequencia,
            @RequestParam String dataPrescricao
    ) {

        prescricaoService.salvarPrescricao(
                pacienteCpf,
                medicamento,
                dosagem,
                frequencia,
                dataPrescricao
        );

        return ResponseEntity.ok("Prescrição criada com sucesso!");
    }
}