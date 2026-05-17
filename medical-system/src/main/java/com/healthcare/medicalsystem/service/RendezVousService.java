package com.healthcare.medicalsystem.service;

import com.healthcare.medicalsystem.dto.RendezVousDTO;
import com.healthcare.medicalsystem.entity.RendezVous;
import com.healthcare.medicalsystem.entity.StatutRendezVous;
import com.healthcare.medicalsystem.mapper.RendezVousMapper;
import com.healthcare.medicalsystem.repository.RendezVousRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final RendezVousMapper rendezVousMapper;



    public RendezVousDTO create(RendezVousDTO dto) {
        dto.setStatut(StatutRendezVous.PLANIFIE);
        return rendezVousMapper.toDTO(rendezVousRepository.save(rendezVousMapper.toEntity(dto)));
    }

    public RendezVousDTO update(Long id, RendezVousDTO dto) {
        RendezVous existing = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RDV introuvable"));
        existing.setDateRendezVous(dto.getDateRendezVous());
        existing.setStatut(dto.getStatut());
        return rendezVousMapper.toDTO(rendezVousRepository.save(existing));
    }

    public void annuler(Long id) {
        RendezVous rdv = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RDV introuvable"));
        rdv.setStatut(StatutRendezVous.ANNULE);
        rendezVousRepository.save(rdv);
    }

    public List<RendezVousDTO> findAll() {
        return rendezVousMapper.toDTOList(rendezVousRepository.findAll());
    }

    public List<RendezVousDTO> findByPatient(Long patientId) {
        return rendezVousMapper.toDTOList(rendezVousRepository.findByPatientId(patientId));
    }

    public List<RendezVousDTO> findByMedecin(Long medecinId) {
        return rendezVousMapper.toDTOList(rendezVousRepository.findByMedecinId(medecinId));
    }
}