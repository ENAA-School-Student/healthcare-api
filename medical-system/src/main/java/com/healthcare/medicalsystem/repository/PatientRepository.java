package com.healthcare.medicalsystem.repository;

import com.healthcare.medicalsystem.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Remplace findAll() par version paginée + tri
    Page<Patient> findAll(Pageable pageable);
    // Recherche paginée par nom
    Page<Patient> findByNom(String nom, Pageable pageable);

}