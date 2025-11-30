package com.ProjetoExtensao.Projeto.controllers;

import com.ProjetoExtensao.Projeto.models.ResponsavelSaude;
import com.ProjetoExtensao.Projeto.servicos.ResponsavelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsaveis")
@RequiredArgsConstructor
public class ResponsavelController {

    private final ResponsavelService responsavelService;

    @GetMapping
    public ResponseEntity<List<ResponsavelSaude>> listarTodos() {
        return ResponseEntity.ok(responsavelService.findAllResponsaveis());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ResponsavelSaude> buscarPorEmail(@PathVariable String email) {
        ResponsavelSaude responsavel = responsavelService.findResponsavelByEmail(email);

        if (responsavel == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(responsavel);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ResponsavelSaude> buscarPorNome(@PathVariable String nome) {
        ResponsavelSaude responsavel = responsavelService.findResponsavelByNome(nome);
        return ResponseEntity.ok(responsavel);
    }
}