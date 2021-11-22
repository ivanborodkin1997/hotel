package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GuestRegistrationDto {

    private int id;
    private Integer hotelRoomId;
    private Integer guestId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private List<Integer> facilityIds;

}
