package org.example.service;

import org.example.dto.GuestDto;
import org.example.model.Guest;

import java.util.List;

public interface GuestService {

    long getGuestsNumber();

    List<GuestDto> getAllGuests();

    GuestDto getGuest(Integer id);

    Guest saveGuest(GuestDto guest);

    void deleteGuest(Integer id);

}


