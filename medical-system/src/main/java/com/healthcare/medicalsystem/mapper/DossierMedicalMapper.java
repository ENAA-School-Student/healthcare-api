package com.healthcare.medicalsystem.mapper;

import com.healthcare.medicalsystem.dto.DossierMedicalDTO;
import com.healthcare.medicalsystem.entity.DossierMedical;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DossierMedicalMapper {
    @Mapping(source = "patient.id", target = "patientId")
    DossierMedicalDTO toDTO(DossierMedical dossier);

    @Mapping(source = "patientId", target = "patient.id")
    DossierMedical toEntity(DossierMedicalDTO dto);
}