package com.healthcare.medicalsystem.service;

import com.healthcare.medicalsystem.dto.MedecinDTO;
import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.entity.Medecin;
import com.healthcare.medicalsystem.entity.Patient;
import com.healthcare.medicalsystem.mapper.MedecinMapper;
import com.healthcare.medicalsystem.repository.MedecinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedecinService {

    private final MedecinRepository medecinRepository;
    private final MedecinMapper medecinMapper;

    public MedecinDTO create(MedecinDTO dto) {
        return medecinMapper.toDTO(medecinRepository.save(medecinMapper.toEntity(dto)));
    }

    public MedecinDTO update(Long id, MedecinDTO dto) {
        Medecin existing = medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médecin introuvable"));
        existing.setNom(dto.getNom());
        existing.setSpecialite(dto.getSpecialite());
        existing.setEmail(dto.getEmail());
        existing.setTelephone(dto.getTelephone());
        return medecinMapper.toDTO(medecinRepository.save(existing));
    }

    public void delete(Long id) {

        medecinRepository.deleteById(id);
    }

    public MedecinDTO findById(Long id) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medecin introuvable"));
        return medecinMapper.toDTO(medecin);
    }


    public Page<MedecinDTO> findAll(Pageable pageable) {
        return medecinMapper.toDTOList(medecinRepository.findAll(pageable));
    }

    public Page<MedecinDTO> searchBySpecialite(String specialite, Pageable pageable) {
        return medecinMapper.toDTOList(medecinRepository.findBySpecialite(specialite, pageable));
    }
}