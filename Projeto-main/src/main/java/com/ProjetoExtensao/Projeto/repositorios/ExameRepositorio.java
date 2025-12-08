package com.ProjetoExtensao.Projeto.repositorios;


import com.ProjetoExtensao.Projeto.models.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ExameRepositorio extends JpaRepository<Exame, Long> {
    List<Exame> findByPacienteId(Long pacienteId);
    List<Exame> findByResponsavelId(Long responsavelId);
    List<Exame> findByPacienteIdAndResponsavelId(Long pacienteId, Long responsavelId);

}
