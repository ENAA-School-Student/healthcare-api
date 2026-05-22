package com.healthcare.medicalsystem.mapper;

import com.healthcare.medicalsystem.dto.MedecinDTO;
import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.entity.Medecin;
import com.healthcare.medicalsystem.entity.Patient;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedecinMapper {
    MedecinDTO toDTO(Medecin medecin);
    Medecin toEntity(MedecinDTO dto);
    List<MedecinDTO> toDTOList(List<Medecin> medecins);
    Page<MedecinDTO> toDTOList(Page<Medecin> medecins);

}