package com.ProjetoExtensao.Projeto.servicos;

import com.ProjetoExtensao.Projeto.infra.DateTimeFormatter;
import com.ProjetoExtensao.Projeto.models.Internacao;
import com.ProjetoExtensao.Projeto.models.Paciente;
import com.ProjetoExtensao.Projeto.models.ResponsavelSaude;
import com.ProjetoExtensao.Projeto.repositorios.InternacaoRepositorio;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InternacaoService {

    private InternacaoRepositorio internacaoRepositorio;
    private PacienteService pacienteService;
    private ResponsavelService responsavelService;

    public List<Internacao> findByPacienteId(Long pacienteId) {
        return internacaoRepositorio.findByPacienteId(pacienteId);
    }

    public List<Internacao> findByResponsavelId(Long responsavelId) {
        return internacaoRepositorio.findByResponsavelId(responsavelId);
    }

    public List<Internacao> findByHospital(String nomeHospital) {
        return internacaoRepositorio.findByHospitalContainingIgnoreCase(nomeHospital);
    }

    public List<Internacao> findByPeriodo(String inicio, String fim) {
        return internacaoRepositorio.findByDataEntradaBetween(
                LocalDate.parse(inicio, DateTimeFormatter.DATE_TIME_FORMATTER),
                LocalDate.parse(fim, DateTimeFormatter.DATE_TIME_FORMATTER)
        );
    }

    public void salvarInternacao(String pacienteCpf, String dataEntrada, String dataAlta,
                                 String hospital, String motivo, String responsavelNome) {

        Paciente paciente = pacienteService.findPacienteByCpf(pacienteCpf);
        ResponsavelSaude responsavel = responsavelService.findResponsavelByNome(responsavelNome);

        Internacao internacao = new Internacao();
        internacao.setDataEntrada(LocalDate.parse(dataEntrada, DateTimeFormatter.DATE_TIME_FORMATTER));

        if (dataAlta != null && !dataAlta.isEmpty()) {
            internacao.setDataAlta(LocalDate.parse(dataAlta, DateTimeFormatter.DATE_TIME_FORMATTER));
        }

        internacao.setMotivo(motivo);
        internacao.setHospital(hospital);
        internacao.setResponsavel(responsavel);
        internacao.setPaciente(paciente);

        internacaoRepositorio.save(internacao);
    }
}