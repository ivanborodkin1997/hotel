package org.example.mapper;

import org.example.dto.HotelRoomDto;
import org.example.model.GuestRegistration;
import org.example.model.HotelRoom;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HotelRoomMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "capacity", target = "capacity")
    @Mapping(source = "amountOfStars", target = "amountOfStars")
    @Mapping(source = "registrations", target = "registrationIds", qualifiedByName = "registrationIds")
    HotelRoomDto toDto(HotelRoom hotelRoom);

    @IterableMapping(elementTargetType = HotelRoomDto.class)
    List<HotelRoomDto> toDtos(List<HotelRoom> hotelRooms);

    @InheritInverseConfiguration
    @Mapping(target = "registrations", ignore = true)
    HotelRoom fromDto(HotelRoomDto hotelRoomDto);

    @Named("registrationIds")
    default List<Integer> getRegistrations(List<GuestRegistration> guestRegistrations) {
        return guestRegistrations.stream().map(GuestRegistration::getId).collect(Collectors.toList());
    }

}
