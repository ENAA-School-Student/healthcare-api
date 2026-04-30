package com.healthcare.medicalsystem.controller;

import com.healthcare.medicalsystem.dto.DossierMedicalDTO;
import com.healthcare.medicalsystem.service.DossierMedicalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dossiers")
@RequiredArgsConstructor
public class DossierMedicalController {

    private final DossierMedicalService dossierService;

    @PostMapping
    @Operation(summary = "Ajouter un DossierMedical")
    public ResponseEntity<DossierMedicalDTO> create(@RequestBody DossierMedicalDTO dto) {
        return ResponseEntity.ok(dossierService.create(dto));
    }

    @PutMapping("/{id}/diagnostic")
    @Operation(summary = "Ajouter Diagnostic")
    public ResponseEntity<DossierMedicalDTO> addDiagnostic(@PathVariable Long id, @RequestBody String diagnostic) {
        return ResponseEntity.ok(dossierService.addDiagnostic(id, diagnostic));
    }

    @PutMapping("/{id}/observations")
    @Operation(summary = "Ajouter Observations")
    public ResponseEntity<DossierMedicalDTO> addObservations(@PathVariable Long id, @RequestBody String observations) {
        return ResponseEntity.ok(dossierService.addObservations(id, observations));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Trouver par Patient")
    public ResponseEntity<DossierMedicalDTO> findByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(dossierService.findByPatient(patientId));
    }
}