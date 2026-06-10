package com.healthcare.medicalsystem.service;

import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.entity.Patient;
import com.healthcare.medicalsystem.mapper.PatientMapper;
import com.healthcare.medicalsystem.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @CacheEvict(value = "patients", allEntries = true)
    public PatientDTO create(PatientDTO dto) {
        Patient patient = patientMapper.toEntity(dto);
        return patientMapper.toDTO(patientRepository.save(patient));
    }

    @CacheEvict(value = "patients", allEntries = true)
    public PatientDTO update(Long id, PatientDTO dto) {
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setEmail(dto.getEmail());
        existing.setTelephone(dto.getTelephone());
        existing.setDateNaissance(dto.getDateNaissance());
        return patientMapper.toDTO(patientRepository.save(existing));
    }

    @CacheEvict(value = "patients", allEntries = true)
    public void delete(Long id) {

        patientRepository.deleteById(id);
    }

    public PatientDTO findById(Long id) {
         Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));
        return patientMapper.toDTO(patient);
    }

    @Cacheable(value = "patients", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<PatientDTO> findAll(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(patientMapper::toDTO);

    }

    public Page<PatientDTO> searchByNom(String nom, Pageable pageable) {
        return patientRepository.findByNom(nom, pageable)
                .map(patientMapper::toDTO);
    }

}