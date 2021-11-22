package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class HotelRoomDto {

    private int id;
    private Double price;
    private Integer number;
    private Integer capacity;
    private Integer amountOfStars;
    private List<Integer> registrationIds;

}
