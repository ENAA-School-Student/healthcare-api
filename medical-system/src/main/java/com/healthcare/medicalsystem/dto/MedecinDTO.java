package com.healthcare.medicalsystem.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MedecinDTO {
    private Long id;
    private String nom;
    private String specialite;
    private String email;
    private String telephone;
}