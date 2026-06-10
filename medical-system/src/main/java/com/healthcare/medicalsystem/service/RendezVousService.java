package com.healthcare.medicalsystem.service;

import com.healthcare.medicalsystem.dto.RendezVousDTO;
import com.healthcare.medicalsystem.entity.RendezVous;
import com.healthcare.medicalsystem.entity.StatutRendezVous;
import com.healthcare.medicalsystem.mapper.RendezVousMapper;
import com.healthcare.medicalsystem.repository.RendezVousRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final RendezVousMapper rendezVousMapper;


    @CacheEvict(value = "rendezVous", allEntries = true)
    public RendezVousDTO create(RendezVousDTO dto) {
        dto.setStatut(StatutRendezVous.PLANIFIE);
        return rendezVousMapper.toDTO(rendezVousRepository.save(rendezVousMapper.toEntity(dto)));
    }

    @CacheEvict(value = "rendezVous", allEntries = true)
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

    @Cacheable(value = "rendezVous", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<RendezVousDTO> findAll(Pageable pageable) {
        return rendezVousRepository.findAll(pageable)
                .map(rendezVousMapper::toDTO);
    }

    public Page<RendezVousDTO> findByStatut(StatutRendezVous statut, Pageable pageable) {
        return rendezVousRepository.findByStatut(statut, pageable)
                .map(rendezVousMapper::toDTO);
    }

    public List<RendezVousDTO> findByPatient(Long patientId) {
        return rendezVousMapper.toDTOList(rendezVousRepository.findByPatientId(patientId));
    }

    public List<RendezVousDTO> findByMedecin(Long medecinId) {
        return rendezVousMapper.toDTOList(rendezVousRepository.findByMedecinId(medecinId));
    }
}