package com.healthcare.medicalsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "dossier_medical")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DossierMedical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnostic;

    private String observations;

    private LocalDate dateCreation;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}