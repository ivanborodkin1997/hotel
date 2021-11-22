package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.GuestDto;
import org.example.exception.EmptyInputException;
import org.example.exception.EmptyListException;
import org.example.exception.NoSuchGuestException;
import org.example.mapper.GuestMapper;
import org.example.model.Guest;
import org.example.repository.GuestRepo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepo guestRepo;
    private final GuestMapper guestMapper;

    @Override
    public long getGuestsNumber() {
        return guestRepo.count();
    }

    @Override
    public List<GuestDto> getAllGuests() {
        List<Guest> guestsList = guestRepo.findAll();
        List<GuestDto> guestDtosList = guestMapper.toDtos(guestsList);
        if (guestsList.isEmpty()) {
            throw new EmptyListException();
        }
        return guestDtosList;
    }

    @Override
    public GuestDto getGuest(Integer id) {
        return guestMapper.toDto(guestRepo.findById(id).orElseThrow(() -> new NoSuchGuestException(id)));
    }

    @Override
    public Guest saveGuest(GuestDto guestDto) {
        if (guestDto.getSurname().isEmpty() || guestDto.getName().isEmpty()) {
            throw new EmptyInputException();
        }
        return guestRepo.save(guestMapper.fromDto(guestDto));
    }

    @Override
    public void deleteGuest(Integer id) {
        try {
            guestRepo.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NoSuchGuestException(id);
        }
    }
}
