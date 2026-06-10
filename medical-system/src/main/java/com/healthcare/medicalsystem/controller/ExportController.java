package com.healthcare.medicalsystem.controller;

import com.healthcare.medicalsystem.service.PdfExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class ExportController {

    private final PdfExportService pdfExportService;

    @GetMapping("/dossier/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN')")
    public ResponseEntity<byte[]> exportDossier(@PathVariable Long patientId) throws IOException {
        byte[] pdf = pdfExportService.generateDossierPdf(patientId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=dossier_" + patientId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/rendez-vous/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN','PATIENT')")
    public ResponseEntity<byte[]> exportRendezVous(@PathVariable Long patientId) throws IOException {
        byte[] pdf = pdfExportService.generateRendezVousPdf(patientId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=rdv_" + patientId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
