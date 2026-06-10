package com.healthcare.medicalsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "medecin")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Medecin extends User{

    private String nom;
    private String specialite;
    private String telephone;

    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL)
    private List<RendezVous> rendezVousList;
}