package com.ProjetoExtensao.Projeto.repositorios;

import com.ProjetoExtensao.Projeto.models.EventoSentinela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoSentinelaRepositorio extends JpaRepository<EventoSentinela, Long> {
    List<EventoSentinela> findByPacienteId(Long pacienteId);
}