package com.ProjetoExtensao.Projeto.servicos;

import com.ProjetoExtensao.Projeto.infra.DateTimeFormatter;
import com.ProjetoExtensao.Projeto.models.Prescricao;
import com.ProjetoExtensao.Projeto.models.Paciente;
import com.ProjetoExtensao.Projeto.repositorios.PrescricaoRepositorio;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PrescricaoService {

    private PrescricaoRepositorio prescricaoRepositorio;
    private PacienteService pacienteService;

    public List<Prescricao> findPrescricoesByPacienteId(Long pacienteId) {
        return prescricaoRepositorio.findByPacienteId(pacienteId);
    }

    public List<Prescricao> findPrescricoesByPacienteCpf(String cpf) {
        Paciente paciente = pacienteService.findPacienteByCpf(cpf);
        return prescricaoRepositorio.findByPacienteId(paciente.getId());
    }

    public void salvarPrescricao(String pacienteCpf, String medicamento, String dosagem,
                                 String frequencia, String dataPrescricao) {

        Paciente paciente = pacienteService.findPacienteByCpf(pacienteCpf);

        Prescricao prescricao = new Prescricao();
        prescricao.setMedicamento(medicamento);
        prescricao.setDosagem(dosagem);
        prescricao.setFrequencia(frequencia);
        prescricao.setDataPrescricao(LocalDate.parse(dataPrescricao, DateTimeFormatter.DATE_TIME_FORMATTER));
        prescricao.setPaciente(paciente);

        prescricaoRepositorio.save(prescricao);
    }
}