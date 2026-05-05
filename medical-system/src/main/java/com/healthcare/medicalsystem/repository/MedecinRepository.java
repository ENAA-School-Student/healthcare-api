package com.healthcare.medicalsystem.repository;

import com.healthcare.medicalsystem.entity.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {

}