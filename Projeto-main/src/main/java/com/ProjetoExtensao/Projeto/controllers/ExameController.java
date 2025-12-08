package com.ProjetoExtensao.Projeto.controllers;

import com.ProjetoExtensao.Projeto.models.Exame;
import com.ProjetoExtensao.Projeto.servicos.ExameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
public class ExameController {

    private final ExameService exameService;

    @GetMapping("/paciente/{cpf}")
    public ResponseEntity<List<Exame>> getExamesByPacienteCpf(@PathVariable String cpf) {
        List<Exame> exames = exameService.findExamesByPacienteCpf(cpf);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/responsavel/{responsavelId}")
    public ResponseEntity<List<Exame>> getExamesByResponsavel(@PathVariable Long responsavelId) {
        List<Exame> exames = exameService.findExamesByResponsavelId(responsavelId);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<Exame>> getExamesByPacienteAndResponsavel(
            @RequestParam Long pacienteId,
            @RequestParam Long responsavelId
    ) {
        List<Exame> exames = exameService.findExamesByPacienteIdAndResponsavelId(pacienteId, responsavelId);
        return ResponseEntity.ok(exames);
    }

    @PostMapping
    public ResponseEntity<String> criarExame(
            @RequestParam String pacienteCpf,
            @RequestParam String tipoExame,
            @RequestParam String dataExame,
            @RequestParam String responsavelNome,
            @RequestParam(required = false) String resultado
    ) {

        LocalDate data = LocalDate.parse(dataExame);

        exameService.salvarExame(
                pacienteCpf,
                tipoExame,
                data,
                responsavelNome,
                resultado
        );

        return ResponseEntity.ok("Exame criado com sucesso!");
    }
}