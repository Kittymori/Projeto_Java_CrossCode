package com.ProjetoExtensao.Projeto.controllers;

import com.ProjetoExtensao.Projeto.models.Internacao;
import com.ProjetoExtensao.Projeto.servicos.InternacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internacoes")
@RequiredArgsConstructor
public class InternacaoController {

    private final InternacaoService internacaoService;

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Internacao>> getInternacoesByPacienteId(@PathVariable Long pacienteId) {
        List<Internacao> internacoes = internacaoService.findByPacienteId(pacienteId);
        return ResponseEntity.ok(internacoes);
    }

    @GetMapping("/responsavel/{responsavelId}")
    public ResponseEntity<List<Internacao>> getInternacoesByResponsavelId(@PathVariable Long responsavelId) {
        List<Internacao> internacoes = internacaoService.findByResponsavelId(responsavelId);
        return ResponseEntity.ok(internacoes);
    }

    @GetMapping("/hospital")
    public ResponseEntity<List<Internacao>> getInternacoesByHospital(@RequestParam String nome) {
        List<Internacao> internacoes = internacaoService.findByHospital(nome);
        return ResponseEntity.ok(internacoes);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Internacao>> getInternacoesByPeriodo(
            @RequestParam String inicio,
            @RequestParam String fim
    ) {
        List<Internacao> internacoes = internacaoService.findByPeriodo(inicio, fim);
        return ResponseEntity.ok(internacoes);
    }

    @PostMapping
    public ResponseEntity<String> criarInternacao(
            @RequestParam String pacienteCpf,
            @RequestParam String dataEntrada,
            @RequestParam(required = false) String dataAlta,
            @RequestParam String hospital,
            @RequestParam String motivo,
            @RequestParam String responsavelNome
    ) {

        internacaoService.salvarInternacao(
                pacienteCpf,
                dataEntrada,
                dataAlta,
                hospital,
                motivo,
                responsavelNome
        );

        return ResponseEntity.ok("Internação registrada com sucesso!");
    }
}