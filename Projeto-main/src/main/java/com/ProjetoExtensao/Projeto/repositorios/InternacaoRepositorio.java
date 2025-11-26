package com.ProjetoExtensao.Projeto.repositorios;

import com.ProjetoExtensao.Projeto.models.Internacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InternacaoRepositorio extends JpaRepository<Internacao, Long> {

    List<Internacao> findByPacienteId(Long pacienteId);

    List<Internacao> findByResponsavelSaudeId(Long responsavelId);

    List<Internacao> findByHospitalContainingIgnoreCase(String hospital);

    List<Internacao> findByDataEntradaBetween(LocalDate inicio, LocalDate fim);
}