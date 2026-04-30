package com.healthcare.medicalsystem.repository;

import com.healthcare.medicalsystem.entity.DossierMedical;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DossierMedicalRepository extends JpaRepository<DossierMedical, Long> {
    Optional<DossierMedical> findByPatientId(Long patientId);
}