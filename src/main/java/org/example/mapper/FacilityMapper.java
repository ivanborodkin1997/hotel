package org.example.mapper;

import org.example.dto.FacilityDto;
import org.example.model.Facility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacilityMapper {

    FacilityDto toDto(Facility facility);

    List<FacilityDto> toDtos(List<Facility> facilities);

    @Mapping(target = "guestRegistration", ignore = true)
    Facility fromDto(FacilityDto facilityDto);

}
