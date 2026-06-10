package com.healthcare.medicalsystem.dto;

import com.healthcare.medicalsystem.entity.StatutRendezVous;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RendezVousDTO implements Serializable {
    private Long id;
    private LocalDateTime dateRendezVous;
    private StatutRendezVous statut;
    private Long patientId;
    private Long medecinId;
}