package com.ProjetoExtensao.Projeto.servicos;

import com.ProjetoExtensao.Projeto.infra.DateTimeFormatter;
import com.ProjetoExtensao.Projeto.models.Vacina;
import com.ProjetoExtensao.Projeto.models.Paciente;
import com.ProjetoExtensao.Projeto.repositorios.VacinaRepositorio;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class VacinaService {

    private VacinaRepositorio vacinaRepositorio;
    private PacienteService pacienteService;

    public List<Vacina> findVacinasByPacienteId(Long pacienteId) {
        return vacinaRepositorio.findByPacienteId(pacienteId);
    }

    public List<Vacina> findVacinasByPacienteCpf(String cpf) {
        Paciente paciente = pacienteService.findPacienteByCpf(cpf);
        return vacinaRepositorio.findByPacienteId(paciente.getId());
    }

    public void salvarVacina(String pacienteCpf, String nomeVacina, String dataAplicacao) {

        Paciente paciente = pacienteService.findPacienteByCpf(pacienteCpf);

        Vacina vacina = new Vacina();
        vacina.setNomeVacina(nomeVacina);
        vacina.setDataAplicacao(LocalDate.parse(dataAplicacao, DateTimeFormatter.DATE_TIME_FORMATTER));
        vacina.setPaciente(paciente);

        vacinaRepositorio.save(vacina);
    }
}