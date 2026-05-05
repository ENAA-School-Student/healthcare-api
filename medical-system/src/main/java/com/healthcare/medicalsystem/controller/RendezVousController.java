package com.healthcare.medicalsystem.controller;

import com.healthcare.medicalsystem.dto.RendezVousDTO;
import com.healthcare.medicalsystem.service.RendezVousService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rendezvous")
@RequiredArgsConstructor
public class RendezVousController {

    private final RendezVousService rendezVousService;

    @GetMapping("/date")
    @Operation(summary =  "Trouver List rendez_vous par date")
    public ResponseEntity<List<RendezVousDTO>> finRendezVous(@RequestBody LocalDateTime date) {
        return ResponseEntity.ok(rendezVousService.finRendezVousByDate(date));
    }

    @PostMapping
    @Operation(summary = "Ajouter un RendezVous")
    public ResponseEntity<RendezVousDTO> create(@RequestBody RendezVousDTO dto) {
        return ResponseEntity.ok(rendezVousService.create(dto));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Modifier un RendezVous")
    public ResponseEntity<RendezVousDTO> update(@PathVariable Long id, @RequestBody RendezVousDTO dto) {
        return ResponseEntity.ok(rendezVousService.update(id, dto));
    }


    @PutMapping("/{id}/annuler")
    @Operation(summary = "annuler un RendezVous")
    public ResponseEntity<Void> annuler(@PathVariable Long id) {
        rendezVousService.annuler(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    @Operation(summary = "Lister les RendezVous")
    public ResponseEntity<List<RendezVousDTO>> findAll() {
        return ResponseEntity.ok(rendezVousService.findAll());
    }


    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Trouver par Patient")
    public ResponseEntity<List<RendezVousDTO>> findByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(rendezVousService.findByPatient(patientId));
    }


    @GetMapping("/medecin/{medecinId}")
    @Operation(summary = "Trouver par Medecin")
    public ResponseEntity<List<RendezVousDTO>> findByMedecin(@PathVariable Long medecinId) {
        return ResponseEntity.ok(rendezVousService.findByMedecin(medecinId));
    }


}