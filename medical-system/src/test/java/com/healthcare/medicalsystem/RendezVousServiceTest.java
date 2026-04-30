package com.healthcare.medicalsystem;

import com.healthcare.medicalsystem.dto.MedecinDTO;
import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.dto.RendezVousDTO;
import com.healthcare.medicalsystem.service.MedecinService;
import com.healthcare.medicalsystem.service.PatientService;
import com.healthcare.medicalsystem.service.RendezVousService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RendezVousServiceTest {

    @Autowired
    private RendezVousService rendezVousService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private MedecinService medecinService;

    private static Long rdvId;
    private static Long patientId;
    private static Long medecinId;

    // ─────────────────────────────────────────────
    // Préparation : créer un patient et un médecin
    // ─────────────────────────────────────────────
    @BeforeAll
    static void setup(
            @Autowired PatientService pService,
            @Autowired MedecinService mService
    ) {
        PatientDTO p = new PatientDTO();
        p.setNom("Hasni");
        p.setPrenom("Omar");
        p.setEmail("omar.rdv@test.com");
        p.setTelephone("0600000001");
        p.setDateNaissance(LocalDate.of(1985, 3, 10));
        patientId = pService.create(p).getId();

        MedecinDTO m = new MedecinDTO();
        m.setNom("Bennis");
        m.setSpecialite("Pediatrie");
        m.setEmail("bennis.rdv@hopital.ma");
        m.setTelephone("0522334455");
        medecinId = mService.create(m).getId();
    }

    // ─────────────────────────────────────────────
    // 1. Créer un rendez-vous
    // ─────────────────────────────────────────────
    @Test
    @Order(1)
    void testCreerRendezVous() {
        RendezVousDTO dto = new RendezVousDTO();
        dto.setDateRendezVous(LocalDateTime.of(2025, 6, 15, 10, 30));
        dto.setPatientId(patientId);
        dto.setMedecinId(medecinId);

        RendezVousDTO saved = rendezVousService.create(dto);

        assertNotNull(saved.getId());
        assertEquals("PLANIFIE", saved.getStatut(), "Le statut initial doit être PLANIFIE");
        assertEquals(patientId, saved.getPatientId());
        assertEquals(medecinId, saved.getMedecinId());

        rdvId = saved.getId();
    }

    // ─────────────────────────────────────────────
    // 2. Lister tous les rendez-vous
    // ─────────────────────────────────────────────
    @Test
    @Order(2)
    void testFindAllRendezVous() {
        List<RendezVousDTO> liste = rendezVousService.findAll();

        assertNotNull(liste);
        assertFalse(liste.isEmpty());
    }

    // ─────────────────────────────────────────────
    // 3. Trouver les RDV d'un patient
    // ─────────────────────────────────────────────
    @Test
    @Order(3)
    void testFindByPatient() {
        List<RendezVousDTO> liste = rendezVousService.findByPatient(patientId);

        assertFalse(liste.isEmpty(), "Le patient doit avoir au moins un RDV");
        assertEquals(patientId, liste.get(0).getPatientId());
    }

    // ─────────────────────────────────────────────
    // 4. Trouver les RDV d'un médecin
    // ─────────────────────────────────────────────
    @Test
    @Order(4)
    void testFindByMedecin() {
        List<RendezVousDTO> liste = rendezVousService.findByMedecin(medecinId);

        assertFalse(liste.isEmpty(), "Le médecin doit avoir au moins un RDV");
        assertEquals(medecinId, liste.get(0).getMedecinId());
    }

    // ─────────────────────────────────────────────
    // 5. Modifier un rendez-vous
    // ─────────────────────────────────────────────
    @Test
    @Order(5)
    void testModifierRendezVous() {
        RendezVousDTO dto = new RendezVousDTO();
        dto.setDateRendezVous(LocalDateTime.of(2025, 7, 20, 14, 0));
        dto.setStatut("CONFIRME");

        RendezVousDTO updated = rendezVousService.update(rdvId, dto);

        assertEquals("CONFIRME", updated.getStatut());
        assertEquals(LocalDateTime.of(2025, 7, 20, 14, 0), updated.getDateRendezVous());
    }

    // ─────────────────────────────────────────────
    // 6. Annuler un rendez-vous
    // ─────────────────────────────────────────────
    @Test
    @Order(6)
    void testAnnulerRendezVous() {
        assertDoesNotThrow(() -> rendezVousService.annuler(rdvId));

        // Vérifier que le statut est bien ANNULE
        RendezVousDTO rdv = rendezVousService.findAll()
                .stream()
                .filter(r -> r.getId().equals(rdvId))
                .findFirst()
                .orElseThrow();

        assertEquals("ANNULE", rdv.getStatut());
    }

    // ─────────────────────────────────────────────
    // 7. RDV inexistant → exception
    // ─────────────────────────────────────────────
    @Test
    @Order(7)
    void testAnnulerRdvInexistant() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> rendezVousService.annuler(9999L)
        );
        assertTrue(ex.getMessage().contains("introuvable"));
    }
}