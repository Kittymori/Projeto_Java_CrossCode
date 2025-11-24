package com.ProjetoExtensao.Projeto.repositorios;

import com.ProjetoExtensao.Projeto.models.Prescricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescricaoRepositorio extends JpaRepository<Prescricao, Long> {

    List<Prescricao> findByPacienteId(Long pacienteId);
}