package com.ProjetoExtensao.Projeto.controllers;

import com.ProjetoExtensao.Projeto.models.EventoSentinela;
import com.ProjetoExtensao.Projeto.servicos.EventoSentinelaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos-sentinela")
@RequiredArgsConstructor
public class EventoSentinelaController {

    private final EventoSentinelaService eventoSentinelaService;

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<EventoSentinela>> getEventosByPacienteId(@PathVariable Long pacienteId) {
        List<EventoSentinela> eventos = eventoSentinelaService.findEventosByPacienteId(pacienteId);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<EventoSentinela>> getEventosByPacienteCpf(@PathVariable String cpf) {
        List<EventoSentinela> eventos = eventoSentinelaService.findEventosByPacienteCpf(cpf);
        return ResponseEntity.ok(eventos);
    }

    @PostMapping
    public ResponseEntity<String> criarEvento(@RequestBody EventoSentinelaRequest request) {

        eventoSentinelaService.salvarEvento(
                request.pacienteCpf(),
                request.tipoEvento(),
                request.dataOcorrido()
        );

        return ResponseEntity.ok("Evento sentinela registrado com sucesso!");
    }

    public record EventoSentinelaRequest(
            String pacienteCpf,
            String tipoEvento,
            String dataOcorrido
    ) {}
}