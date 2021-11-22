package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.HotelRoomDto;
import org.example.model.HotelRoom;
import org.example.model.SortingMethod;
import org.example.service.HotelRoomService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class HotelRoomController {

    private final HotelRoomService hotelRoomService;

    @GetMapping("/all")
    public ResponseEntity<List<HotelRoomDto>> getAllRooms() {
        return ResponseEntity.ok(hotelRoomService.getAllHotelRoom());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<HotelRoomDto> getRoom(@PathVariable int id) {
        return ResponseEntity.ok(hotelRoomService.getHotelRoom(id));
    }

    @GetMapping("/free")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<Long> getAmountFreeRoom() {
        return ResponseEntity.ok(hotelRoomService.getAmountFreeRooms());
    }

    @GetMapping("/rooms-sort")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<HotelRoomDto>> getAllRoomsSortBy(@RequestParam SortingMethod sortingMethod) {
        return ResponseEntity.ok(hotelRoomService.getAllRoomsSortBy(sortingMethod));
    }

    @GetMapping("/free-rooms-sort")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<HotelRoomDto>> getAllFreeRoomsSortBy(@RequestParam SortingMethod sortingMethod) {
        return ResponseEntity.ok(hotelRoomService.getAllFreeRoomsSortBy(sortingMethod));
    }

    @GetMapping("/free-rooms")
    @PreAuthorize("hasAnyAuthority('developers:read')")
    public ResponseEntity<List<HotelRoomDto>> getAllFreeRooms() {
        return ResponseEntity.ok(hotelRoomService.getFreeRooms());
    }

    @GetMapping("/free-rooms-by-date")
    @PreAuthorize("hasAnyAuthority('developers:read')")
    public ResponseEntity<List<HotelRoomDto>> freeRoomsByDate(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(hotelRoomService.getFreeRoomsByDate(date));
    }

    @GetMapping("/last-three-guests/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<List<Integer>> getLastThreeGuest(@PathVariable int id) {
        return ResponseEntity.ok(hotelRoomService.getTheLastTreeGuests(id));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<HotelRoom> addRoom(@RequestBody HotelRoomDto hotelRoom) {
        return ResponseEntity.ok(hotelRoomService.saveHotelRoom(hotelRoom));
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<HotelRoom> updateRoom(@RequestBody HotelRoomDto hotelRoom) {
        return ResponseEntity.ok(hotelRoomService.saveHotelRoom(hotelRoom));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<String> deleteRoom(@PathVariable int id) {
        hotelRoomService.deleteHotelRoom(id);
        return ResponseEntity.ok("HotelRoom with ID = " + id + " was deleted");
    }

}
