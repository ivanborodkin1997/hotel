package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.GuestRegistrationDto;
import org.example.model.GuestRegistration;
import org.example.model.SortingMethod;
import org.example.service.GuestRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registrations")
@PreAuthorize("hasAuthority('developers:write')")
public class GuestRegistrationController {

    private final GuestRegistrationService guestRegistrationService;

    @GetMapping("/all")
    public ResponseEntity<List<GuestRegistrationDto>> getAllRegistration() {
        return ResponseEntity.ok(guestRegistrationService.getAllGuestRegistration());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestRegistrationDto> getRegistration(@PathVariable int id) {
        return ResponseEntity.ok(guestRegistrationService.getRegistration(id));
    }

    @GetMapping("/all-by-sort/{sortingMethod}")
    public ResponseEntity<List<GuestRegistrationDto>> getAllBySort(@PathVariable SortingMethod sortingMethod) {
        return ResponseEntity.ok(guestRegistrationService.getAllRegistrationsBySort(sortingMethod));
    }

    @GetMapping("/payment/{guestId}")
    public ResponseEntity<Double> showPaymentForOrder(@PathVariable int guestId) {
        return ResponseEntity.ok(guestRegistrationService.getRoomCharge(guestId));
    }

    @PostMapping()
    public ResponseEntity<GuestRegistrationDto> guestRegistration(@RequestBody GuestRegistrationDto guestRegistrationDto) {
        return ResponseEntity.ok(guestRegistrationService.addGuestInRoom(guestRegistrationDto));
    }

    @PutMapping()
    public ResponseEntity<GuestRegistrationDto> updateRegistration(@RequestBody GuestRegistrationDto guestRegistrationDto) {
        return ResponseEntity.ok(guestRegistrationService.updateRegistration(guestRegistrationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegistration(@PathVariable int id) {
        guestRegistrationService.deleteRegistration(id);
        return ResponseEntity.ok("Registration with ID = " + id + " was deleted");
    }

}
