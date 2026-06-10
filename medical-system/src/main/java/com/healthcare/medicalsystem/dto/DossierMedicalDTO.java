package com.healthcare.medicalsystem.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DossierMedicalDTO implements Serializable {
    private Long id;
    private String diagnostic;
    private String observations;
    private LocalDate dateCreation;
    private Long patientId;
}