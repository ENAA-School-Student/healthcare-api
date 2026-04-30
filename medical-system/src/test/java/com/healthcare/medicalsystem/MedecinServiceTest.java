package com.healthcare.medicalsystem;

import com.healthcare.medicalsystem.dto.MedecinDTO;
import com.healthcare.medicalsystem.service.MedecinService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedecinServiceTest {

    @Autowired
    private MedecinService medecinService;

    private static Long medecinId;

    // ─────────────────────────────────────────────
    // 1. Créer un médecin
    // ─────────────────────────────────────────────
    @Test
    @Order(1)
    void testCreerMedecin() {
        MedecinDTO dto = new MedecinDTO();
        dto.setNom("Alaoui");
        dto.setSpecialite("Cardiologie");
        dto.setEmail("alaoui@hopital.ma");
        dto.setTelephone("0522001122");

        MedecinDTO saved = medecinService.create(dto);

        assertNotNull(saved.getId(), "L'ID doit être généré");
        assertEquals("Alaoui", saved.getNom());
        assertEquals("Cardiologie", saved.getSpecialite());

        medecinId = saved.getId();
    }

    // ─────────────────────────────────────────────
    // 2. Lister tous les médecins
    // ─────────────────────────────────────────────
    @Test
    @Order(2)
    void testFindAllMedecins() {
        List<MedecinDTO> liste = medecinService.findAll();

        assertNotNull(liste);
        assertFalse(liste.isEmpty(), "La liste doit contenir au moins un médecin");
    }

    // ─────────────────────────────────────────────
    // 3. Modifier un médecin
    // ─────────────────────────────────────────────
    @Test
    @Order(3)
    void testModifierMedecin() {
        MedecinDTO dto = new MedecinDTO();
        dto.setNom("Alaoui");
        dto.setSpecialite("Neurologie");       // nouvelle spécialité
        dto.setEmail("alaoui.neuro@hopital.ma");
        dto.setTelephone("0522001122");

        MedecinDTO updated = medecinService.update(medecinId, dto);

        assertEquals("Neurologie", updated.getSpecialite());
        assertEquals("alaoui.neuro@hopital.ma", updated.getEmail());
    }

    // ─────────────────────────────────────────────
    // 4. Supprimer un médecin
    // ─────────────────────────────────────────────
    @Test
    @Order(4)
    void testSupprimerMedecin() {
        assertDoesNotThrow(() -> medecinService.delete(medecinId),
                "La suppression ne doit pas lever d'exception");
    }
}