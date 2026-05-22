package com.healthcare.medicalsystem.controller;

import com.healthcare.medicalsystem.dto.MedecinDTO;
import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.service.MedecinService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medecins")
@RequiredArgsConstructor
public class MedecinController {

    private final MedecinService medecinService;

    @PostMapping
    @Operation(summary = "Ajouter un Medecin")
    public ResponseEntity<MedecinDTO> create(@RequestBody MedecinDTO dto) {
        return ResponseEntity.ok(medecinService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un Medecin")
    public ResponseEntity<MedecinDTO> update(@PathVariable Long id, @RequestBody MedecinDTO dto) {
        return ResponseEntity.ok(medecinService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un Medecin")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medecinService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Lister les Medecins")
    public ResponseEntity<Page<MedecinDTO>> findAll(
        @PageableDefault(size = 10, sort = "nom") Pageable pageable) {
        return ResponseEntity.ok(medecinService.findAll(pageable));
    }

    @GetMapping
    @Operation(summary = "Lister les Medecins")
    public ResponseEntity<Page<PatientDTO>> findAll(
            @PageableDefault(size = 10, sort = "nom") Pageable pageable) {
        return ResponseEntity.ok(patientService.findAll(pageable));
    }

    // Ajouter recherche par nom :
    @GetMapping("/search")
    public ResponseEntity<Page<PatientDTO>> search(
            @RequestParam String nom,
            @PageableDefault(size = 10, sort = "nom") Pageable pageable) {
        return ResponseEntity.ok(patientService.searchByNom(nom, pageable));
    }
}