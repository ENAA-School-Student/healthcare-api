package com.healthcare.medicalsystem.service;

import com.healthcare.medicalsystem.dto.DossierMedicalDTO;
import com.healthcare.medicalsystem.entity.DossierMedical;
import com.healthcare.medicalsystem.mapper.DossierMedicalMapper;
import com.healthcare.medicalsystem.repository.DossierMedicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DossierMedicalService {

    private final DossierMedicalRepository dossierRepository;
    private final DossierMedicalMapper dossierMapper;

    @CacheEvict(value = "dossier", allEntries = true)
    public DossierMedicalDTO create(DossierMedicalDTO dto) {
        dto.setDateCreation(LocalDate.now());
        return dossierMapper.toDTO(dossierRepository.save(dossierMapper.toEntity(dto)));
    }

    public DossierMedicalDTO addDiagnostic(Long id, String diagnostic) {
        DossierMedical d = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));
        d.setDiagnostic(diagnostic);
        return dossierMapper.toDTO(dossierRepository.save(d));
    }

    public DossierMedicalDTO addObservations(Long id, String observations) {
        DossierMedical d = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));
        d.setObservations(observations);
        return dossierMapper.toDTO(dossierRepository.save(d));
    }

    @Cacheable(value = "patients", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public DossierMedicalDTO findByPatient(Long patientId) {
        return dossierRepository.findByPatientId(patientId)
                .map(dossierMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));
    }
}