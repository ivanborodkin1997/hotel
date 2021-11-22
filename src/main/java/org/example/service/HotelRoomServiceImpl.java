package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.HotelRoomDto;
import org.example.exception.EmptyInputException;
import org.example.exception.EmptyListException;
import org.example.exception.NoSuchRoomException;
import org.example.mapper.HotelRoomMapper;
import org.example.model.*;
import org.example.repository.HotelRoomRepo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelRoomServiceImpl implements HotelRoomService{

    private final HotelRoomRepo hotelRoomRepo;
    private final HotelRoomMapper hotelRoomMapper;

    @Override
    public List<HotelRoomDto> getAllHotelRoom() {
        List<HotelRoom> hotelRoomsList = hotelRoomRepo.findAll();
        List<HotelRoomDto> hotelRoomDtosList = hotelRoomMapper.toDtos(hotelRoomsList);
        if (hotelRoomsList.isEmpty()) {
            throw new EmptyListException();
        }
        return hotelRoomDtosList;
    }

    @Override
    public HotelRoomDto getHotelRoom(Integer id) {
        return hotelRoomMapper.toDto(hotelRoomRepo.findById(id).orElseThrow(() -> new NoSuchRoomException(id)));
    }

    @Override
    public HotelRoom saveHotelRoom(HotelRoomDto hotelRoomDto) {
        if (hotelRoomDto.getNumber() == null || hotelRoomDto.getAmountOfStars() == null ||
                hotelRoomDto.getCapacity() == null || hotelRoomDto.getPrice() == null) {
            throw new EmptyInputException();
        }
        return hotelRoomRepo.save(hotelRoomMapper.fromDto(hotelRoomDto));
    }

    @Override
    public void deleteHotelRoom(Integer id) {
        try {
            hotelRoomRepo.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NoSuchRoomException(id);
        }
    }

    @Override
    public List<HotelRoomDto> getAllRoomsSortBy(SortingMethod sortingMethod) {
        List<HotelRoom> hotelRoomsList = hotelRoomRepo.findAll();
        List<HotelRoomDto> hotelRoomDtosList = hotelRoomMapper.toDtos(hotelRoomsList);
        if (hotelRoomDtosList.isEmpty()) {
            throw new EmptyListException();
        }
        return sortRooms(hotelRoomDtosList, sortingMethod);
    }

    @Override
    public List<HotelRoomDto> getAllFreeRoomsSortBy(SortingMethod sortingMethod) {
        List<HotelRoomDto> allFreeHotelRoomsList = getFreeRooms();

        if (allFreeHotelRoomsList.isEmpty()) {
            throw new EmptyListException();
        }
        return sortRooms(allFreeHotelRoomsList, sortingMethod);
    }

    @Override
    public List<Integer> getTheLastTreeGuests(int roomId) {

        List<Integer> allGuestsList = new ArrayList<>();
        HotelRoom hotelRoom = hotelRoomRepo.findById(roomId).orElseThrow(() -> new NoSuchRoomException(roomId));
        List<GuestRegistration> guestRegistrationsList = hotelRoom.getRegistrations();
        guestRegistrationsList.sort(Comparator.comparing(GuestRegistration::getCheckInDate));

        try {
            guestRegistrationsList.forEach(guestRegistration -> allGuestsList.add(guestRegistration.getGuest().getId()));
        } catch (IndexOutOfBoundsException exception) {
            throw new IndexOutOfBoundsException();
        }

        return allGuestsList.subList(allGuestsList.size() - 3, allGuestsList.size());

    }

    @Override
    public long getAmountFreeRooms() {
        return getFreeRooms().size();
    }

    @Override
    public List<HotelRoomDto> getFreeRoomsByDate(LocalDate localDate) {
        List<HotelRoom> freeByDateList = new ArrayList<>();

        hotelRoomRepo.findAll().forEach(hotelRoom -> {
            if (isFreeByDate(hotelRoom, localDate)) {
                freeByDateList.add(hotelRoom);
            }
        });
        return hotelRoomMapper.toDtos(freeByDateList);
    }

    @Override
    public List<HotelRoomDto> getFreeRooms() {
        List<HotelRoom> freeRoomList = new ArrayList<>();

        hotelRoomRepo.findAll().forEach(hotelRoom -> {
            if (isFreeByDate(hotelRoom, LocalDate.now())) {
                freeRoomList.add(hotelRoom);
            }

        });
        return hotelRoomMapper.toDtos(freeRoomList);
    }

    private List<HotelRoomDto> sortRooms(List<HotelRoomDto> hotelRoomsList, SortingMethod sortingMethod) {
        switch (sortingMethod) {
            case BY_PRICE:
                return hotelRoomsList.stream().sorted(Comparator.comparing(HotelRoomDto::getPrice)).collect(Collectors.toList());
            case BY_CAPACITY:
                return hotelRoomsList.stream().sorted(Comparator.comparing(HotelRoomDto::getCapacity)).collect(Collectors.toList());
            case BY_STARS:
                return hotelRoomsList.stream().sorted(Comparator.comparing(HotelRoomDto::getAmountOfStars)).collect(Collectors.toList());
        }
        return null;
    }

    private boolean isFreeByDate(HotelRoom hotelRoom, LocalDate day) {
        for (GuestRegistration guestRegistration : hotelRoom.getRegistrations()) {
            if (day.equals(guestRegistration.getCheckInDate()) || day.equals(guestRegistration.getCheckOutDate())
                    || (day.isAfter(guestRegistration.getCheckInDate()) && day.isBefore(guestRegistration.getCheckOutDate()))) {
                return false;
            }
        }
        return true;
    }
}
