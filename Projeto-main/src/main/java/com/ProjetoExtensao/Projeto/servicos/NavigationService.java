package com.ProjetoExtensao.Projeto.servicos;

import com.ProjetoExtensao.Projeto.view.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class NavigationService {
    @Lazy
    @Autowired
    private TelaLogin telaLogin;

    @Lazy
    @Autowired
    private TelaGeral telaGeral;

    @Lazy
    @Autowired
    private TelaPacientes telaPacientes;

    @Autowired
    @Lazy
    private TelaCadastroPacientes telaCadastroPacientes;

    @Lazy
    @Autowired
    private TelaConsultas consulta;

    @Autowired
    @Lazy
    private TelaAgendamentoConsulta telaAgendamentoConsulta;

    // --- NOVAS INJEÇÕES DE DEPENDÊNCIA ---
    @Lazy
    @Autowired
    private TelaConsultaEventosSentinelas telaConsultaEventosSentinelas;

    @Lazy
    @Autowired
    private TelaRegistroEventosSentinelas telaRegistroEventosSentinelas;

    // -------------------------------------

    public void abrirTelaLogin(){
        telaLogin.setVisible(true);
    }

    public void abrirTelaGeral() {
        telaGeral.setVisible(true);
    }

    public void abrirTelaPacientes() {
        telaPacientes.setVisible(true);
    }

    public void abrirTelaCadastroPacientes() {
        telaCadastroPacientes.setVisible(true);
    }

    public void abrirTelaConsultas() {
        consulta.setVisible(true);
    }

    public void abrirTelaAgendamentoConsultas() {
        telaAgendamentoConsulta.setVisible(true);
    }

    public void abrirTelaConsultaEventosSentinelas() {
        telaConsultaEventosSentinelas.setVisible(true);
    }

    public void abrirTelaRegistroEventosSentinelas() {
        telaRegistroEventosSentinelas.setVisible(true);
    }
}