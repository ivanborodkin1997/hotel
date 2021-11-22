package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GuestDto {

    private int id;
    private String name;
    private String surname;
    private List<Integer> registrationIds;

}
