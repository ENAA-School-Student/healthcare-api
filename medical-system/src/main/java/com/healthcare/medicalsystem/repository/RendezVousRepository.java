package com.healthcare.medicalsystem.repository;

import com.healthcare.medicalsystem.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    // Derived Query
    List<RendezVous> findByPatientId(Long patientId);

    // @Query JPQL
    @Query("SELECT r FROM RendezVous r WHERE r.medecin.id = :medecinId")
    List<RendezVous> findByMedecinIdJPQL(@Param("medecinId") Long medecinId);

    // @Query SQL natif avec jointure
    @Query(value = "SELECT r.* FROM rendez_vous r " +
            "INNER JOIN patient p ON r.patient_id = p.id " +
            "WHERE p.nom = :nom", nativeQuery = true)
    List<RendezVous> findByPatientNomNative(@Param("nom") String nom);
}