package com.ProjetoExtensao.Projeto.servicos;

import com.ProjetoExtensao.Projeto.models.*;
import com.ProjetoExtensao.Projeto.repositorios.ProntuarioMedicoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ProntuarioMedicoService {

    private ProntuarioMedicoRepositorio prontuarioRepositorio;
    private PacienteService pacienteService;

    public ProntuarioMedico findByPacienteCpf(String cpf) {
        return prontuarioRepositorio.findByPacienteCpf(cpf)
                .orElseGet(() -> criarProntuarioParaPaciente(cpf));
    }

    public ProntuarioMedico criarProntuarioParaPaciente(String cpf) {
        Paciente paciente = pacienteService.findPacienteByCpf(cpf);

        ProntuarioMedico prontuario = new ProntuarioMedico();
        prontuario.setPaciente(paciente);

        return prontuarioRepositorio.save(prontuario);
    }

    public void adicionarConsulta(String cpf, Consulta consulta) {
        ProntuarioMedico prontuario = findByPacienteCpf(cpf);
        prontuario.addConsulta(consulta);
        prontuarioRepositorio.save(prontuario);
    }

    public void adicionarPrescricao(String cpf, Prescricao prescricao) {
        ProntuarioMedico prontuario = findByPacienteCpf(cpf);
        prontuario.addPrescricao(prescricao);
        prontuarioRepositorio.save(prontuario);
    }

    public void adicionarExame(String cpf, Exame exame) {
        ProntuarioMedico prontuario = findByPacienteCpf(cpf);
        prontuario.addExame(exame);
        prontuarioRepositorio.save(prontuario);
    }

    public void adicionarVacina(String cpf, Vacina vacina) {
        ProntuarioMedico prontuario = findByPacienteCpf(cpf);
        prontuario.addVacina(vacina);
        prontuarioRepositorio.save(prontuario);
    }

    public void adicionarEvento(String cpf, EventoSentinela evento) {
        ProntuarioMedico prontuario = findByPacienteCpf(cpf);
        prontuario.addEvento(evento);
        prontuarioRepositorio.save(prontuario);
    }

    public void adicionarInternacao(String cpf, Internacao internacao) {
        ProntuarioMedico prontuario = findByPacienteCpf(cpf);
        prontuario.addInternacao(internacao);
        prontuarioRepositorio.save(prontuario);
    }

    public List<Consulta> buscarConsultasPorData(String cpf, LocalDate data) {
        ProntuarioMedico prontuario = findByPacienteCpf(cpf);

        return prontuario.getConsultas().stream()
                .filter(c -> c.getData().equals(data))
                .toList();
    }

    public List<Consulta> buscarConsultasPorResponsavel(String cpf, String nomeResponsavel) {
        ProntuarioMedico prontuario = findByPacienteCpf(cpf);

        return prontuario.getConsultas().stream()
                .filter(c -> c.getResponsavelSaude().getNomeCompleto().equalsIgnoreCase(nomeResponsavel))
                .toList();
    }

    public String gerarResumo(String cpf) {
        ProntuarioMedico prontuario = findByPacienteCpf(cpf);
        return prontuario.gerarResumo();
    }
}