package com.healthcare.medicalsystem.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DossierMedicalDTO {
    private Long id;
    private String diagnostic;
    private String observations;
    private LocalDate dateCreation;
    private Long patientId;
}