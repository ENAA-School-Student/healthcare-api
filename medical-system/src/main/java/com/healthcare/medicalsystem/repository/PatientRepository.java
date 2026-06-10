package com.healthcare.medicalsystem.repository;

import com.healthcare.medicalsystem.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PatientRepository extends JpaRepository<Patient, Long> {


    Page<Patient> findAll(Pageable pageable);

    Page<Patient> findByNom(String nom, Pageable pageable);

    Optional<Patient> findByUsername(String username);

}