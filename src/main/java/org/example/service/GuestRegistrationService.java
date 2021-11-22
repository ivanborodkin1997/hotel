package org.example.service;

import org.example.dto.GuestRegistrationDto;
import org.example.model.GuestRegistration;
import org.example.model.SortingMethod;

import java.util.List;

public interface GuestRegistrationService {

    List<GuestRegistrationDto> getAllGuestRegistration();

    void deleteRegistration(int id);

    GuestRegistrationDto getRegistration(int id);

    List<GuestRegistrationDto> getAllRegistrationsBySort(SortingMethod sortingMethod);

    GuestRegistrationDto addGuestInRoom(GuestRegistrationDto guestRegistrationDto);

    GuestRegistrationDto updateRegistration(GuestRegistrationDto guestRegistrationDto);

    double getRoomCharge(int guestId);

}
