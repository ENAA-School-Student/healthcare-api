package com.healthcare.medicalsystem.controller;

import com.healthcare.medicalsystem.configuration.JwtUtils;
import com.healthcare.medicalsystem.dto.LoginRequest;
import com.healthcare.medicalsystem.dto.RegisterRequest;
import com.healthcare.medicalsystem.entity.Medecin;
import com.healthcare.medicalsystem.entity.Patient;
import com.healthcare.medicalsystem.entity.Role;
import com.healthcare.medicalsystem.entity.User;
import com.healthcare.medicalsystem.repository.MedecinRepository;
import com.healthcare.medicalsystem.repository.PatientRepository;
import com.healthcare.medicalsystem.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Le nom d'utilisateur est déjà pris");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("L'email est déjà utilisé");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User savedUser = null;

        switch (request.getRole()) {
            case PATIENT -> {
                Patient patient = new Patient();
                patient.setUsername(request.getUsername());
                patient.setNom(request.getNom());
                patient.setPrenom(request.getPrenom());
                patient.setEmail(request.getEmail());
                patient.setPassword(encodedPassword);
                patient.setRole(Role.PATIENT);
                patient.setTelephone(request.getTelephone());
                patient.setDateNaissance(request.getDateNaissance());
                savedUser = patientRepository.save(patient);
            }
            case MEDECIN -> {
                Medecin medecin = new Medecin();
                medecin.setUsername(request.getUsername());
                medecin.setNom(request.getNom());
                medecin.setEmail(request.getEmail());
                medecin.setPassword(encodedPassword);
                medecin.setRole(Role.MEDECIN);
                medecin.setTelephone(request.getTelephone());
                medecin.setSpecialite(request.getSpecialite());
                savedUser = medecinRepository.save(medecin);
            }
            case ADMIN -> {
                User admin = new User();
                admin.setUsername(request.getUsername());
                admin.setEmail(request.getEmail());
                admin.setPassword(encodedPassword);
                admin.setRole(Role.ADMIN);
                savedUser = userRepository.save(admin);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtUtils.generateToken(savedUser.getUsername(), savedUser.getRole().name()));
        response.put("type", "Bearer");
        response.put("role", savedUser.getRole().name());
        response.put("username", savedUser.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
             Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
             if(authentication.isAuthenticated()) {
                 User user = userRepository.findByUsername(request.getUsername())
                         .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

                 Map<String, Object> response = new HashMap<>();
                 response.put("token", jwtUtils.generateToken(user.getUsername(), user.getRole().name()));
                 response.put("type", "Bearer");
                 response.put("role", user.getRole().name());
                 response.put("username", user.getUsername());

                 return ResponseEntity.ok(response);
             }
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");
        }
    }


}
