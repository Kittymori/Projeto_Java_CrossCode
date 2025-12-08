package com.ProjetoExtensao.Projeto.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "internacoes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Internacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataEntrada;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAlta;

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private String hospital;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private ResponsavelSaude responsavel;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    public Internacao(LocalDate dataEntrada, LocalDate dataAlta, String motivo,
                      String hospital, ResponsavelSaude responsavel, Paciente paciente) {
        this.dataEntrada = dataEntrada;
        this.dataAlta = dataAlta;
        this.motivo = motivo;
        this.hospital = hospital;
        this.responsavel = responsavel;
        this.paciente = paciente;
    }
}