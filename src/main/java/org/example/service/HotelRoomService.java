package org.example.service;

import org.example.dto.HotelRoomDto;
import org.example.model.HotelRoom;
import org.example.model.SortingMethod;

import java.time.LocalDate;
import java.util.List;

public interface HotelRoomService {

    List<HotelRoomDto> getAllHotelRoom();

    HotelRoomDto getHotelRoom(Integer id);

    List<HotelRoomDto> getAllRoomsSortBy(SortingMethod sortingMethod);

    List<HotelRoomDto> getAllFreeRoomsSortBy(SortingMethod sortingMethod);

    long getAmountFreeRooms();

    List<HotelRoomDto> getFreeRooms();

    List<Integer> getTheLastTreeGuests(int roomId);

    HotelRoom saveHotelRoom(HotelRoomDto hotelRoomDto);

    void deleteHotelRoom(Integer id);

    List<HotelRoomDto> getFreeRoomsByDate(LocalDate localDate);

}
