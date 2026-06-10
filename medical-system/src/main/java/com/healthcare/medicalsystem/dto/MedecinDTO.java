package com.healthcare.medicalsystem.dto;

import lombok.*;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MedecinDTO implements Serializable {
    private Long id;
    private String nom;
    private String specialite;
    private String email;
    private String telephone;
}