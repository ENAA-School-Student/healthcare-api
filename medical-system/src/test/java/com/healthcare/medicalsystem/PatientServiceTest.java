package com.healthcare.medicalsystem;

import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.service.PatientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    private static Long patientId;

    // ─────────────────────────────────────────────
    // 1. Créer un patient
    // ─────────────────────────────────────────────
    @Test
    @Order(1)
    void testCreerPatient() {
        PatientDTO dto = new PatientDTO();
        dto.setNom("Ben Ali");
        dto.setPrenom("Youssef");
        dto.setEmail("youssef@test.com");
        dto.setTelephone("0612345678");
        dto.setDateNaissance(LocalDate.of(1990, 5, 20));

        PatientDTO saved = patientService.create(dto);

        assertNotNull(saved.getId(), "L'ID doit être généré automatiquement");
        assertEquals("Ben Ali", saved.getNom());
        assertEquals("Youssef", saved.getPrenom());
        assertEquals("youssef@test.com", saved.getEmail());

        patientId = saved.getId();
    }

    // ─────────────────────────────────────────────
    // 2. Lister tous les patients
    // ─────────────────────────────────────────────
    @Test
    @Order(2)
    void testFindAllPatients() {
        List<PatientDTO> liste = patientService.findAll();

        assertNotNull(liste, "La liste ne doit pas être null");
        assertFalse(liste.isEmpty(), "La liste doit contenir au moins un patient");
    }

    // ─────────────────────────────────────────────
    // 3. Trouver un patient par ID
    // ─────────────────────────────────────────────
    @Test
    @Order(3)
    void testFindPatientParId() {
        PatientDTO found = patientService.findById(patientId);

        assertNotNull(found);
        assertEquals(patientId, found.getId());
        assertEquals("Ben Ali", found.getNom());
    }

    // ─────────────────────────────────────────────
    // 4. Modifier un patient
    // ─────────────────────────────────────────────
    @Test
    @Order(4)
    void testModifierPatient() {
        PatientDTO dto = new PatientDTO();
        dto.setNom("Ben Ali Modifié");
        dto.setPrenom("Youssef");
        dto.setEmail("youssef.modifie@test.com");
        dto.setTelephone("0699999999");
        dto.setDateNaissance(LocalDate.of(1990, 5, 20));

        PatientDTO updated = patientService.update(patientId, dto);

        assertEquals("Ben Ali Modifié", updated.getNom());
        assertEquals("youssef.modifie@test.com", updated.getEmail());
        assertEquals("0699999999", updated.getTelephone());
    }

    // ─────────────────────────────────────────────
    // 5. ID inexistant → exception
    // ─────────────────────────────────────────────
    @Test
    @Order(5)
    void testFindPatientInexistant() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> patientService.findById(9999L),
                "Doit lever une exception si le patient n'existe pas"
        );
        assertTrue(ex.getMessage().contains("introuvable"));
    }

    // ─────────────────────────────────────────────
    // 6. Supprimer un patient
    // ─────────────────────────────────────────────
    @Test
    @Order(6)
    void testSupprimerPatient() {
        assertDoesNotThrow(() -> patientService.delete(patientId),
                "La suppression ne doit pas lever d'exception");

        assertThrows(RuntimeException.class, () -> patientService.findById(patientId));
    }
}