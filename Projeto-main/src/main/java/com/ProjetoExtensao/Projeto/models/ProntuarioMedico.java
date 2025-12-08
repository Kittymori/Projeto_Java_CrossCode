package com.ProjetoExtensao.Projeto.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prontuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")

public class ProntuarioMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @OneToMany
    @JoinColumn(name = "prontuario_id")
    private List<Consulta> consultas = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "prontuario_id")
    private List<Prescricao> prescricoes = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "prontuario_id")
    private List<Exame> exames = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "prontuario_id")
    private List<EventoSentinela> eventosSentinela = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "prontuario_id")
    private List<Internacao> internacoes = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "prontuario_id")
    private List<Vacina> vacinas = new ArrayList<>();

    public void addConsulta(Consulta consulta) {
        consultas.add(consulta);
    }

    public void addPrescricao(Prescricao prescricao) {
        prescricoes.add(prescricao);
    }

    public void addExame(Exame exame) {
        exames.add(exame);
    }

    public void addVacina(Vacina vacina) {
        vacinas.add(vacina);
    }

    public void addEvento(EventoSentinela evento) {
        eventosSentinela.add(evento);
    }

    public void addInternacao(Internacao internacao) {
        internacoes.add(internacao);
    }

    public String gerarResumo() {
        return "Resumo do Prontuário de " + paciente.getNomeCompleto()
                + "\nConsultas: " + consultas.size()
                + "\nPrescrições: " + prescricoes.size()
                + "\nExames: " + exames.size()
                + "\nVacinas: " + vacinas.size()
                + "\nEventos Sentinela: " + eventosSentinela.size()
                + "\nInternações: " + internacoes.size();
    }
}