package com.ProjetoExtensao.Projeto.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "vacinas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")

public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeVacina;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataAplicacao;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    public Vacina(String nomeVacina, LocalDate dataAplicacao, Paciente paciente) {
        this.nomeVacina = nomeVacina;
        this.dataAplicacao = dataAplicacao;
        this.paciente = paciente;
    }
}