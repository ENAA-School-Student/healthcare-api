package com.healthcare.medicalsystem.mapper;

import com.healthcare.medicalsystem.dto.PatientDTO;
import com.healthcare.medicalsystem.dto.RendezVousDTO;
import com.healthcare.medicalsystem.entity.Patient;
import com.healthcare.medicalsystem.entity.RendezVous;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RendezVousMapper {
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "medecin.id", target = "medecinId")
    RendezVousDTO toDTO(RendezVous rdv);

    @Mapping(source = "patientId", target = "patient.id")
    @Mapping(source = "medecinId", target = "medecin.id")
    RendezVous toEntity(RendezVousDTO dto);

    List<RendezVousDTO> toDTOList(List<RendezVous> list);

    Page<RendezVousDTO> toDTOList(Page<RendezVous> rendezVous);

}