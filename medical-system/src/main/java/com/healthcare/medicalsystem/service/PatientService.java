package com.healthcare.medicalsystem.service;

import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.entity.Patient;
import com.healthcare.medicalsystem.mapper.PatientMapper;
import com.healthcare.medicalsystem.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientDTO create(PatientDTO dto) {
        Patient patient = patientMapper.toEntity(dto);
        return patientMapper.toDTO(patientRepository.save(patient));
    }

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

    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    public List<PatientDTO> findAll() {
        return patientMapper.toDTOList(patientRepository.findAll());
    }

    public PatientDTO findById(Long id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));
    }
}