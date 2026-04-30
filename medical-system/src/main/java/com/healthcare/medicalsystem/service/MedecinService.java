package com.healthcare.medicalsystem.service;

import com.healthcare.medicalsystem.dto.MedecinDTO;
import com.healthcare.medicalsystem.entity.Medecin;
import com.healthcare.medicalsystem.mapper.MedecinMapper;
import com.healthcare.medicalsystem.repository.MedecinRepository;
import lombok.RequiredArgsConstructor;
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

    public List<MedecinDTO> findAll() {
        return medecinMapper.toDTOList(medecinRepository.findAll());
    }
}