package com.ProjetoExtensao.Projeto.servicos;

import com.ProjetoExtensao.Projeto.infra.DateTimeFormatter;
import com.ProjetoExtensao.Projeto.models.EventoSentinela;
import com.ProjetoExtensao.Projeto.models.Paciente;
import com.ProjetoExtensao.Projeto.models.TipoEventoSentinela;
import com.ProjetoExtensao.Projeto.repositorios.EventoSentinelaRepositorio;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EventoSentinelaService {

    private EventoSentinelaRepositorio eventoSentinelaRepositorio;
    private PacienteService pacienteService;

    public List<EventoSentinela> findEventosByPacienteId(Long pacienteId) {
        return eventoSentinelaRepositorio.findByPacienteId(pacienteId);
    }

    public List<EventoSentinela> findEventosByPacienteCpf(String cpf) {
        Paciente paciente = pacienteService.findPacienteByCpf(cpf);
        return eventoSentinelaRepositorio.findByPacienteId(paciente.getId());
    }

    public List<EventoSentinela> buscarTodosEventosNaAPI() {
        return eventoSentinelaRepositorio.findAll();
    }

    public void salvarEvento(String pacienteCpf, String tipoEvento, String dataOcorrido) {

        Paciente paciente = pacienteService.findPacienteByCpf(pacienteCpf);

        EventoSentinela evento = new EventoSentinela();

        evento.setTipoEvento(TipoEventoSentinela.getType(tipoEvento));

        evento.setDataOcorrido(LocalDate.parse(dataOcorrido, DateTimeFormatter.DATE_TIME_FORMATTER));

        evento.setPaciente(paciente);

        eventoSentinelaRepositorio.save(evento);
    }
}