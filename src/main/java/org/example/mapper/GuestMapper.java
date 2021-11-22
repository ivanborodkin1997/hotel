package org.example.mapper;

import org.example.dto.GuestDto;
import org.example.model.Guest;
import org.example.model.GuestRegistration;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface GuestMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "registrations", target = "registrationIds", qualifiedByName = "registrationIds")
    GuestDto toDto(Guest guest);

    @IterableMapping(elementTargetType = GuestDto.class)
    List<GuestDto> toDtos(List<Guest> guests);

    @InheritInverseConfiguration
    @Mapping(target = "registrations", ignore = true)
    Guest fromDto(GuestDto guestDto);

    @Named("registrationIds")
    default List<Integer> getRegistrations(List<GuestRegistration> guestRegistrations) {
        return guestRegistrations.stream().map(GuestRegistration::getId).collect(Collectors.toList());
    }

}
