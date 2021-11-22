package org.example.mapper;

import org.example.dto.GuestRegistrationDto;
import org.example.model.Facility;
import org.example.model.GuestRegistration;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GuestRegistrationMapper {

    @Mapping(source = "guest.id", target = "guestId")
    @Mapping(source = "hotelRoom.id", target = "hotelRoomId")
    @Mapping(source = "checkInDate", target = "checkInDate")
    @Mapping(source = "checkOutDate", target = "checkOutDate")
    @Mapping(source = "facilities", target = "facilityIds", qualifiedByName = "facilityIds")
    GuestRegistrationDto toDto(GuestRegistration guestRegistration);

    @IterableMapping(elementTargetType = GuestRegistrationDto.class)
    List<GuestRegistrationDto> toDtos(List<GuestRegistration> guestRegistrations);

    @InheritInverseConfiguration
    @Mapping(target = "facilities", ignore = true)
    @Mapping(target = "hotelRoom", ignore = true)
    @Mapping(target = "guest", ignore = true)
    GuestRegistration fromDto(GuestRegistrationDto guestRegistrationDTO);

    @Named("facilityIds")
    default List<Integer> getFacilities(List<Facility> facilities) {
        return facilities.stream().map(Facility::getId).collect(Collectors.toList());
    }

}
