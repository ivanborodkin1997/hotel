package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.GuestDto;
import org.example.model.Guest;
import org.example.service.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guests")
@PreAuthorize("hasAuthority('developers:write')")
public class GuestController {

    private final GuestService guestService;

    @GetMapping("/all")
    public ResponseEntity<List<GuestDto>> getAllGuests() {
        return ResponseEntity.ok(guestService.getAllGuests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestDto> getGuest(@PathVariable int id) {
        return ResponseEntity.ok(guestService.getGuest(id));
    }

    @GetMapping("/amount")
    public ResponseEntity<Long> getAmountGuests() {
        return ResponseEntity.ok(guestService.getGuestsNumber());
    }

    @PostMapping()
    public ResponseEntity<Guest> addGuest(@RequestBody GuestDto guest) {
        return ResponseEntity.ok(guestService.saveGuest(guest));
    }

    @PutMapping()
    public ResponseEntity<Guest> updateGuest(@RequestBody GuestDto guest) {
        return ResponseEntity.ok(guestService.saveGuest(guest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGuest(@PathVariable int id) {
        guestService.deleteGuest(id);
        return ResponseEntity.ok("Guest with ID = " + id + " was deleted");
    }

}
