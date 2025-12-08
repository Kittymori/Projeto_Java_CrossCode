package com.ProjetoExtensao.Projeto.controllers;

import com.ProjetoExtensao.Projeto.models.Consulta;
import com.ProjetoExtensao.Projeto.models.Paciente;
import com.ProjetoExtensao.Projeto.servicos.ConsultaService;
import com.ProjetoExtensao.Projeto.servicos.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;
    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<String> criarConsulta(
            @RequestParam String pacienteCpf,
            @RequestParam String data,
            @RequestParam String hora,
            @RequestParam String responsavelNome,
            @RequestParam String tipoConsulta
    ) {
        consultaService.salvarConsulta(pacienteCpf, data, hora, responsavelNome, tipoConsulta);
        return ResponseEntity.ok("Consulta cadastrada com sucesso!");
    }

    @GetMapping("/paciente/{cpf}")
    public ResponseEntity<Consulta> buscarConsultaPorPaciente(@PathVariable String cpf) {
        Paciente paciente = pacienteService.findPacienteByCpf(cpf);
        Consulta consulta = consultaService.findConsultaByPaciente(paciente);
        return ResponseEntity.ok(consulta);
    }
}