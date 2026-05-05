package com.healthcare.medicalsystem.repository;

import com.healthcare.medicalsystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {


}