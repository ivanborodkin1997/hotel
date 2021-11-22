package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.exception.*;
import org.example.mapper.*;
import org.example.model.*;
import org.example.repository.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GuestRegistrationServiceImpl implements GuestRegistrationService {

    private final GuestRegistrationRepo guestRegistrationRepo;
    private final GuestRepo guestRepo;
    private final HotelRoomRepo hotelRoomRepo;
    private final FacilityRepo facilityRepo;
    private final GuestRegistrationMapper guestRegistrationMapper;

    @Override
    public List<GuestRegistrationDto> getAllGuestRegistration() {
        List<GuestRegistration> guestRegistrationsList = guestRegistrationRepo.findAll();
        List<GuestRegistrationDto> guestRegistrationDtosList = guestRegistrationMapper.toDtos(guestRegistrationsList);

        if (guestRegistrationDtosList.isEmpty()) {
            throw new EmptyListException();
        }
        return guestRegistrationDtosList;
    }

    @Override
    public GuestRegistrationDto getRegistration(int id) {
        return guestRegistrationMapper.toDto(guestRegistrationRepo.findById(id).orElseThrow(() ->
                new NoSuchRegistrationException(id)));
    }

    @Override
    @Transactional
    public GuestRegistrationDto addGuestInRoom(GuestRegistrationDto guestRegistrationDto) {

        HotelRoom room = hotelRoomRepo.findById(guestRegistrationDto.getHotelRoomId()).orElseThrow(() ->
                new NoSuchRoomException(guestRegistrationDto.getHotelRoomId()));
        Guest guest = guestRepo.findById(guestRegistrationDto.getGuestId()).orElseThrow(() ->
                new NoSuchGuestException(guestRegistrationDto.getGuestId()));

        List<Facility> facilities = new ArrayList<>();
        for (int facilityId : guestRegistrationDto.getFacilityIds()) {
            Facility facility = facilityRepo.findById(facilityId).orElseThrow(() -> new NoSuchFacilityException(facilityId));
            facilities.add(facility);
        }

        GuestRegistration guestRegistration = guestRegistrationMapper.fromDto(guestRegistrationDto);
        guestRegistration.setFacilities(facilities);
        guestRegistration.setHotelRoom(room);
        guestRegistration.setGuest(guest);

        if (isFree(room.getId(), guestRegistration.getCheckInDate(), guestRegistration.getCheckOutDate())) {
            guestRegistrationRepo.save(guestRegistration);
            return guestRegistrationMapper.toDto(guestRegistration);
        } else {
            throw new OccupiedRoomException(room.getId());
        }
    }

    @Override
    @Transactional
    public GuestRegistrationDto updateRegistration(GuestRegistrationDto guestRegistrationDto) {

        HotelRoom room = hotelRoomRepo.findById(guestRegistrationDto.getHotelRoomId()).orElseThrow(() ->
                new NoSuchRoomException(guestRegistrationDto.getHotelRoomId()));
        Guest guest = guestRepo.findById(guestRegistrationDto.getGuestId()).orElseThrow(() ->
                new NoSuchGuestException(guestRegistrationDto.getGuestId()));

        List<Facility> facilities = new ArrayList<>();
        for (int facilityId : guestRegistrationDto.getFacilityIds()) {
            Facility facility = facilityRepo.findById(facilityId).orElseThrow(() -> new NoSuchFacilityException(facilityId));
            facilities.add(facility);
        }

        GuestRegistration guestRegistration = guestRegistrationMapper.fromDto(guestRegistrationDto);
        guestRegistration.setFacilities(facilities);
        guestRegistration.setHotelRoom(room);
        guestRegistration.setGuest(guest);
        guestRegistrationRepo.save(guestRegistration);

        return guestRegistrationMapper.toDto(guestRegistration);
    }

    @Override
    public double getRoomCharge(int guestId) {
        //Находим последнюю регистрацию гостя
        Guest guest = guestRepo.findById(guestId).orElseThrow(() -> new NoSuchGuestException(guestId));
        guest.getRegistrations().sort(Comparator.comparing(GuestRegistration::getCheckInDate).reversed());
        GuestRegistration guestRegistration = guest.getRegistrations().get(0);

        //Считаем сумму оплаты за проживание
        Period period = Period.between(guestRegistration.getCheckInDate(), guestRegistration.getCheckOutDate());
        int days = period.getDays();
        double priceAllDays = days * guestRegistration.getHotelRoom().getPrice();

        //Ищем все услуги по данной регистрации
        List<Facility> facilitiesList = guestRegistration.getFacilities();

        //Считаем сумму оплаты услуг
        double facilityPrice = 0;
        for (Facility facility : facilitiesList) {
            facilityPrice += facility.getFacilityPrice();
        }
        return priceAllDays + facilityPrice;
    }

    @Override
    public List<GuestRegistrationDto> getAllRegistrationsBySort(SortingMethod sortingMethod) {
        switch (sortingMethod) {
            case BY_ALPHABET:
                return guestRegistrationMapper.toDtos(guestRegistrationRepo.findAll(Sort.by(Sort.Direction.ASC, "guest")));
            case BY_DATE:
                return guestRegistrationMapper.toDtos(guestRegistrationRepo.findAllByOrderByCheckOutDateAsc());
        }
        return null;
    }

    @Override
    public void deleteRegistration(int id) {
        try {
            guestRegistrationRepo.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NoSuchRegistrationException(id);
        }
    }

    private boolean isFree(int roomId, LocalDate arrivalDate, LocalDate dateOfDeparture) {
        HotelRoom hotelRoom = hotelRoomRepo.findById(roomId).orElseThrow(() -> new NoSuchRoomException(roomId));
        for (GuestRegistration guestRegistration : hotelRoom.getRegistrations()) {

            boolean guestCheckInDayAfterCheckIn = arrivalDate.isAfter(guestRegistration.getCheckInDate());
            boolean guestCheckInDayBeforeCheckOut = arrivalDate.isBefore(guestRegistration.getCheckOutDate());
            boolean guestCheckInDayBeforeCheckIn = arrivalDate.isBefore(guestRegistration.getCheckInDate());
            boolean guestCheckOutDayAfterCheckOut = dateOfDeparture.isAfter(guestRegistration.getCheckOutDate());
            boolean guestCheckOutDayBeforeCheckOut = dateOfDeparture.isBefore(guestRegistration.getCheckOutDate());
            boolean guestCheckOutDayAfterCheckIn = dateOfDeparture.isAfter(guestRegistration.getCheckInDate());
            boolean daysEquals = arrivalDate.equals(guestRegistration.getCheckInDate()) ||
                    dateOfDeparture.equals(guestRegistration.getCheckOutDate());

            if ((guestCheckInDayBeforeCheckIn && (guestCheckOutDayBeforeCheckOut && guestCheckOutDayAfterCheckIn)) ||
                    ((guestCheckInDayBeforeCheckOut && guestCheckInDayAfterCheckIn) && guestCheckOutDayAfterCheckOut) ||
                    (guestCheckInDayAfterCheckIn && guestCheckOutDayBeforeCheckOut) || daysEquals) {
                return false;
            }
        }
        return true;
    }

}

