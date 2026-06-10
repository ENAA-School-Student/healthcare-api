package com.healthcare.medicalsystem.repository;

import com.healthcare.medicalsystem.entity.Medecin;
import com.healthcare.medicalsystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {

    Page<Medecin> findAll(Pageable pageable);
    Page<Medecin> findBySpecialite(String specialite, Pageable pageable);
    Optional<Medecin> findByUsername(String username);

}