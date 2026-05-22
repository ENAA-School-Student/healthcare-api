package com.healthcare.medicalsystem.repository;

import com.healthcare.medicalsystem.entity.RendezVous;
import com.healthcare.medicalsystem.entity.StatutRendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    Page<RendezVous> findAll(Pageable pageable);
    Page<RendezVous> findByStatut(StatutRendezVous statut, Pageable pageable);

    List<RendezVous> findByPatientId(Long patientId);

    @Query("SELECT r FROM RendezVous r WHERE r.medecin.id = :medecinId")
    List<RendezVous> findByMedecinId(@Param("medecinId") Long medecinId);












//    boolean existsByPatientId(Long id);
}