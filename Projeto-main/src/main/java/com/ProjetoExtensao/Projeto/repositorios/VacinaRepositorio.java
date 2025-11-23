package com.ProjetoExtensao.Projeto.repositorios;

import com.ProjetoExtensao.Projeto.models.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacinaRepositorio extends JpaRepository<Vacina, Long> {
    List<Vacina> findByPacienteId(Long pacienteId);
}