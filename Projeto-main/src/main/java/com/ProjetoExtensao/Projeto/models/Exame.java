package com.ProjetoExtensao.Projeto.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "exames")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoExame;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataExame;

    @Column(columnDefinition = "TEXT")
    private String resultado;

    @ManyToOne
    @JoinColumn(name = "responsavel_id", nullable = false)
    private ResponsavelSaude responsavel;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    public Exame (String tipoExame, LocalDate dataExame, String resultado, ResponsavelSaude responsavel, Paciente paciente) {
        this.tipoExame = tipoExame;
        this.dataExame = dataExame;
        this.resultado = resultado;
        this.responsavel = responsavel;
        this.paciente = paciente;
    }
}