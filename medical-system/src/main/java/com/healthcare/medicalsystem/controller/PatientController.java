package com.healthcare.medicalsystem.controller;

import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.entity.Patient;
import com.healthcare.medicalsystem.mapper.PatientMapper;
import com.healthcare.medicalsystem.repository.PatientRepository;
import com.healthcare.medicalsystem.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;


    @GetMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientDTO> getMyProfile(Authentication authentication) {
        String username = authentication.getName();

        Patient patient = patientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));
        return ResponseEntity.ok(patientMapper.toDTO(patient));
    }

    @PostMapping
    @Operation(summary = "Ajouter un Patient")
    public ResponseEntity<PatientDTO> create(@RequestBody PatientDTO dto) {
        return ResponseEntity.ok(patientService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un Patient")
    public ResponseEntity<PatientDTO> update(@PathVariable Long id, @RequestBody PatientDTO dto) {
        return ResponseEntity.ok(patientService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un Patient")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un Patient par ID")
    public ResponseEntity<PatientDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.findById(id));
    }


    @GetMapping
    public ResponseEntity<Page<PatientDTO>> findAll(
            @PageableDefault(size = 10, sort = "nom") Pageable pageable) {
        return ResponseEntity.ok(patientService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PatientDTO>> search(
            @RequestParam String nom,
            @PageableDefault(size = 10, sort = "nom") Pageable pageable) {
        return ResponseEntity.ok(patientService.searchByNom(nom, pageable));
    }
}