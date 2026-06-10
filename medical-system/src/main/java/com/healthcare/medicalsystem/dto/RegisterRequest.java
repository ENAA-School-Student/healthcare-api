package com.healthcare.medicalsystem.dto;

import com.healthcare.medicalsystem.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    private Role role = Role.PATIENT;

    private String nom;
    private String prenom;
    private String telephone;
    private LocalDate dateNaissance;

    private String specialite;
}
