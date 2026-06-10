package com.healthcare.medicalsystem;

import com.healthcare.medicalsystem.dto.MedecinDTO;
import com.healthcare.medicalsystem.service.MedecinService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.chrono.AbstractChronology;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedecinServiceTest {

    @Autowired
    private MedecinService medecinService;

    private static Long medecinId;


    @Test
    @Order(1)
    void testCreerMedecin() {
        MedecinDTO dto = new MedecinDTO();
        dto.setNom("Acharaoui");
        dto.setSpecialite("Generaliste");
        dto.setEmail("Acharaoui@gmail.com");
        dto.setTelephone("0544444");

        MedecinDTO resultat = medecinService.create(dto);

        assertNotNull(resultat.getId());
        assertEquals("Acharaoui", resultat.getNom());
        assertEquals("Generaliste", resultat.getSpecialite());
        assertEquals("Acharaoui@gmail.com", resultat.getEmail());

        medecinId = resultat.getId();
    }



    @Test
    @Order(2)
    void testFindAllMedecins() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<MedecinDTO> medecinListe = medecinService.findAll(pageable);

        assertNotNull(medecinListe);
        assertFalse(medecinListe.isEmpty());
    }



    @Test
    @Order(3)
    void testModifierMedecin() {
        MedecinDTO dto = new MedecinDTO();
        dto.setNom("Acharaoui.h");
        dto.setSpecialite("Cardiologie");
        dto.setEmail("Acharaoui.hatim@gmail.com");
        dto.setTelephone("0522222");

        MedecinDTO resultatModifier = medecinService.update(medecinId, dto);

        assertEquals("Acharaoui.h", resultatModifier.getNom());
        assertEquals("Cardiologie", resultatModifier.getSpecialite());
        assertEquals("Acharaoui.hatim@gmail.com", resultatModifier.getEmail());
    }


    @Test
    @Order(4)
    void testSupprimerMedecin() {
        medecinService.delete(medecinId);

        assertThrows(RuntimeException.class,
                () -> medecinService.findById(medecinId));
    }
}