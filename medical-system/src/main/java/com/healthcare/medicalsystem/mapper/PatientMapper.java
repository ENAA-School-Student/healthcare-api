package com.healthcare.medicalsystem.mapper;

import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.entity.Patient;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO toDTO(Patient patient);
    Patient toEntity(PatientDTO dto);
    List<PatientDTO> toDTOList(List<Patient> patients);
    Page<PatientDTO> toDTOList(Page<Patient> patients);
}