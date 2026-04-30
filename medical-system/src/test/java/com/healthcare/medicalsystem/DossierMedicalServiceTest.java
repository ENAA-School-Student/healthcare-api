package com.healthcare.medicalsystem;

import com.healthcare.medicalsystem.dto.DossierMedicalDTO;
import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.service.DossierMedicalService;
import com.healthcare.medicalsystem.service.PatientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DossierMedicalServiceTest {

    @Autowired
    private DossierMedicalService dossierService;

    @Autowired
    private PatientService patientService;

    private static Long dossierId;
    private static Long patientId;

    // ─────────────────────────────────────────────
    // Préparation : créer un patient
    // ─────────────────────────────────────────────
    @BeforeAll
    static void setup(@Autowired PatientService pService) {
        PatientDTO p = new PatientDTO();
        p.setNom("Ziani");
        p.setPrenom("Fatima");
        p.setEmail("fatima.dossier@test.com");
        p.setTelephone("0611223344");
        p.setDateNaissance(LocalDate.of(1978, 8, 25));
        patientId = pService.create(p).getId();
    }

    // ─────────────────────────────────────────────
    // 1. Créer un dossier médical
    // ─────────────────────────────────────────────
    @Test
    @Order(1)
    void testCreerDossier() {
        DossierMedicalDTO dto = new DossierMedicalDTO();
        dto.setDiagnostic("Hypertension légère");
        dto.setObservations("Surveiller la tension chaque semaine");
        dto.setPatientId(patientId);

        DossierMedicalDTO saved = dossierService.create(dto);

        assertNotNull(saved.getId(), "L'ID du dossier doit être généré");
        assertEquals("Hypertension légère", saved.getDiagnostic());
        assertNotNull(saved.getDateCreation(), "La date de création doit être renseignée automatiquement");
        assertEquals(LocalDate.now(), saved.getDateCreation());

        dossierId = saved.getId();
    }

    // ─────────────────────────────────────────────
    // 2. Trouver le dossier par patient
    // ─────────────────────────────────────────────
    @Test
    @Order(2)
    void testFindDossierParPatient() {
        DossierMedicalDTO found = dossierService.findByPatient(patientId);

        assertNotNull(found);
        assertEquals(patientId, found.getPatientId());
        assertEquals("Hypertension légère", found.getDiagnostic());
    }

    // ─────────────────────────────────────────────
    // 3. Ajouter / modifier le diagnostic
    // ─────────────────────────────────────────────
    @Test
    @Order(3)
    void testAjouterDiagnostic() {
        DossierMedicalDTO updated = dossierService.addDiagnostic(dossierId, "Hypertension modérée");

        assertEquals("Hypertension modérée", updated.getDiagnostic());
    }

    // ─────────────────────────────────────────────
    // 4. Ajouter des observations
    // ─────────────────────────────────────────────
    @Test
    @Order(4)
    void testAjouterObservations() {
        DossierMedicalDTO updated = dossierService.addObservations(dossierId, "Régime sans sel recommandé");

        assertEquals("Régime sans sel recommandé", updated.getObservations());
    }

    // ─────────────────────────────────────────────
    // 5. Dossier inexistant → exception
    // ─────────────────────────────────────────────
    @Test
    @Order(5)
    void testDossierInexistant() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> dossierService.findByPatient(9999L)
        );
        assertTrue(ex.getMessage().contains("introuvable"));
    }
}