package com.healthcare.medicalsystem.repository;

import com.healthcare.medicalsystem.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {


    List<RendezVous> findRendezVousByDateRendezVous(LocalDateTime date);

    List<RendezVous> findByPatientId(Long patientId);


    @Query("SELECT r FROM RendezVous r WHERE r.medecin.id = :medecinId")
    List<RendezVous> findByMedecinId(@Param("medecinId") Long medecinId);


    @Query(value = "SELECT r.* FROM rendez_vous r " +
            "INNER JOIN patient p ON r.patient_id = p.id " +
            "WHERE p.nom = :nom", nativeQuery = true)
    List<RendezVous> findByPatientNom(@Param("nom") String nom);











//    boolean existsByPatientId(Long id);
}