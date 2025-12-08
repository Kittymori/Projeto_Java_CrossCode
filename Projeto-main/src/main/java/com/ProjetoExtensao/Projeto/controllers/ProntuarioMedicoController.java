package com.ProjetoExtensao.Projeto.controllers;

import com.ProjetoExtensao.Projeto.models.*;
import com.ProjetoExtensao.Projeto.servicos.ProntuarioMedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/prontuario")
@RequiredArgsConstructor
public class ProntuarioMedicoController {

    private final ProntuarioMedicoService prontuarioService;

    @GetMapping("/{cpf}")
    public ResponseEntity<ProntuarioMedico> getProntuarioByCpf(@PathVariable String cpf) {
        ProntuarioMedico prontuario = prontuarioService.findByPacienteCpf(cpf);
        return ResponseEntity.ok(prontuario);
    }

    @PostMapping("/criar/{cpf}")
    public ResponseEntity<ProntuarioMedico> criarProntuario(@PathVariable String cpf) {
        ProntuarioMedico prontuario = prontuarioService.criarProntuarioParaPaciente(cpf);
        return ResponseEntity.ok(prontuario);
    }

    @PostMapping("/{cpf}/consulta")
    public ResponseEntity<String> adicionarConsulta(
            @PathVariable String cpf,
            @RequestBody Consulta consulta) {

        prontuarioService.adicionarConsulta(cpf, consulta);
        return ResponseEntity.ok("Consulta adicionada ao prontuário!");
    }

    @PostMapping("/{cpf}/prescricao")
    public ResponseEntity<String> adicionarPrescricao(
            @PathVariable String cpf,
            @RequestBody Prescricao prescricao) {

        prontuarioService.adicionarPrescricao(cpf, prescricao);
        return ResponseEntity.ok("Prescrição adicionada ao prontuário!");
    }

    @PostMapping("/{cpf}/exame")
    public ResponseEntity<String> adicionarExame(
            @PathVariable String cpf,
            @RequestBody Exame exame) {

        prontuarioService.adicionarExame(cpf, exame);
        return ResponseEntity.ok("Exame adicionado ao prontuário!");
    }

    @PostMapping("/{cpf}/vacina")
    public ResponseEntity<String> adicionarVacina(
            @PathVariable String cpf,
            @RequestBody Vacina vacina) {

        prontuarioService.adicionarVacina(cpf, vacina);
        return ResponseEntity.ok("Vacina adicionada ao prontuário!");
    }

    @PostMapping("/{cpf}/evento")
    public ResponseEntity<String> adicionarEvento(
            @PathVariable String cpf,
            @RequestBody EventoSentinela evento) {

        prontuarioService.adicionarEvento(cpf, evento);
        return ResponseEntity.ok("Evento sentinela adicionado ao prontuário!");
    }

    @PostMapping("/{cpf}/internacao")
    public ResponseEntity<String> adicionarInternacao(
            @PathVariable String cpf,
            @RequestBody Internacao internacao) {

        prontuarioService.adicionarInternacao(cpf, internacao);
        return ResponseEntity.ok("Internação adicionada ao prontuário!");
    }

    @GetMapping("/{cpf}/consultas/data")
    public ResponseEntity<List<Consulta>> buscarConsultasPorData(
            @PathVariable String cpf,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data) {

        List<Consulta> consultas = prontuarioService.buscarConsultasPorData(cpf, data);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/{cpf}/consultas/responsavel")
    public ResponseEntity<List<Consulta>> buscarConsultasPorResponsavel(
            @PathVariable String cpf,
            @RequestParam String nomeResponsavel) {

        List<Consulta> consultas = prontuarioService.buscarConsultasPorResponsavel(cpf, nomeResponsavel);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/{cpf}/resumo")
    public ResponseEntity<String> gerarResumo(@PathVariable String cpf) {
        String resumo = prontuarioService.gerarResumo(cpf);
        return ResponseEntity.ok(resumo);
    }
}