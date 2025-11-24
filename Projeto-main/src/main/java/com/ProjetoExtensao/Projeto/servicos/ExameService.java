package com.ProjetoExtensao.Projeto.servicos;

import com.ProjetoExtensao.Projeto.models.Exame;
import com.ProjetoExtensao.Projeto.models.Paciente;
import com.ProjetoExtensao.Projeto.models.ResponsavelSaude;
import com.ProjetoExtensao.Projeto.repositorios.ExameRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ExameService {

    private ExameRepositorio exameRepositorio;
    private PacienteService pacienteService;
    private ResponsavelService responsavelService;

    public List<Exame> findExamesByPacienteCpf(String cpf) {
        Paciente paciente = pacienteService.findPacienteByCpf(cpf);
        return exameRepositorio.findByPacienteId(paciente.getId());
    }

    public List<Exame> findExamesByResponsavelId(Long responsavelId) {
        return exameRepositorio.findByResponsavelId(responsavelId);
    }

    public List<Exame> findExamesByPacienteIdAndResponsavelId(Long pacienteId, Long responsavelId) {
        return exameRepositorio.findByPacienteIdAndResponsavelId(pacienteId, responsavelId);
    }

    public void salvarExame(String pacienteCpf, String tipoExame, LocalDate dataExame, String responsavelNome, String resultado) {
        Paciente paciente = pacienteService.findPacienteByCpf(pacienteCpf);
        ResponsavelSaude responsavel = responsavelService.findResponsavelByNome(responsavelNome);

        Exame exame = new Exame();
        exame.setTipoExame(tipoExame);
        exame.setDataExame(dataExame);
        exame.setResultado(resultado);
        exame.setResponsavel(responsavel);
        exame.setPaciente(paciente);

        exameRepositorio.save(exame);
    }

}
