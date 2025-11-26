package com.ProjetoExtensao.Projeto.repositorios;

import com.ProjetoExtensao.Projeto.models.ProntuarioMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProntuarioMedicoRepositorio extends JpaRepository<ProntuarioMedico, Long> {

    Optional<ProntuarioMedico> findByPacienteId(Long pacienteId);

    Optional<ProntuarioMedico> findByPacienteCpf(String cpf);
}