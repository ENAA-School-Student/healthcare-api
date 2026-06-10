package com.healthcare.medicalsystem.service;

import com.healthcare.medicalsystem.entity.DossierMedical;
import com.healthcare.medicalsystem.entity.RendezVous;
import com.healthcare.medicalsystem.repository.DossierMedicalRepository;
import com.healthcare.medicalsystem.repository.RendezVousRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfExportService {

    private final DossierMedicalRepository dossierMedicalRepository;
    private final RendezVousRepository rendezVousRepository;

    public byte[] generateDossierPdf(Long patientId) throws IOException {
        DossierMedical dossier = dossierMedicalRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Dossier Médical")
                .setFontSize(20).setBold());
        document.add(new Paragraph("Patient : " + dossier.getPatient().getNom()
                + " " + dossier.getPatient().getPrenom()));
        document.add(new Paragraph("Diagnostic : " + dossier.getDiagnostic()));
        document.add(new Paragraph("Observations : " + dossier.getObservations()));
        document.add(new Paragraph("Date : " + dossier.getDateCreation()));

        document.close();
        return baos.toByteArray();
    }

    public byte[] generateRendezVousPdf(Long patientId) throws IOException {
        List<RendezVous> rdvList = rendezVousRepository.findByPatientId(patientId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Liste des Rendez-Vous").setFontSize(18).setBold());

        for (RendezVous rdv : rdvList) {
            document.add(new Paragraph("- " + rdv.getDateRendezVous()
                    + " | " + rdv.getStatut()
                    + " | Dr " + rdv.getMedecin().getNom()));
        }

        document.close();
        return baos.toByteArray();
    }
}
