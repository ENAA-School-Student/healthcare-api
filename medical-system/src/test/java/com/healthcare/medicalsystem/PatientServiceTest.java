package com.healthcare.medicalsystem;

import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.service.PatientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    @Test
    @Order(1)
    void testCreerPatient() {
        PatientDTO dto = new PatientDTO();
        dto.setNom("Acharaoui");
        dto.setPrenom("hatim");
        dto.setEmail("hatim@gmail.com");
        dto.setTelephone("061234");
        dto.setDateNaissance(LocalDate.of(1993, 6, 25));

        PatientDTO resultat = patientService.create(dto);

        assertNotNull(resultat.getId());
        assertEquals("Acharaoui", resultat.getNom());
        assertEquals("hatim", resultat.getPrenom());
        assertEquals("hatim@gmail.com", resultat.getEmail());

        patientId = resultat.getId();
    }


    @Test
    @Order(2)
    void testFindAllPatients() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<PatientDTO> patientListe = patientService.findAll(pageable);

        assertNotNull(patientListe);
        assertFalse(patientListe.isEmpty());
    }


    @Test
    @Order(3)
    void testFindPatientParId() {
        PatientDTO patientdto = patientService.findById(patientId);

        assertNotNull(patientdto);
        assertEquals(patientId, patientdto.getId());
        assertEquals("Acharaoui", patientdto.getNom());
        assertEquals("hatim", patientdto.getPrenom());
    }


    @Test
    @Order(4)
    void testModifierPatient() {
        PatientDTO dto = new PatientDTO();
        dto.setNom("Acharaoui A");
        dto.setPrenom("hatim A");
        dto.setEmail("hatim.A@gmail.com");
        dto.setTelephone("067777");
        dto.setDateNaissance(LocalDate.of(1993, 6, 25));

        PatientDTO resultatModifier = patientService.update(patientId, dto);

        assertEquals("Acharaoui A", resultatModifier.getNom());
        assertEquals("hatim.A@gmail.com", resultatModifier.getEmail());
        assertEquals("067777", resultatModifier.getTelephone());
    }



    @Test
    @Order(5)
    void testSupprimerPatient() {
        patientService.delete(patientId);

        assertThrows(RuntimeException.class,
                () -> patientService.findById(patientId));
    }
}