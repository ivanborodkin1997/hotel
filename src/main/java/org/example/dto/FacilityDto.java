package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class FacilityDto {

    private int id;
    private String facilityName;
    private String facilityCategory;
    private Double facilityPrice;
    private LocalDate facilityDateTime;

}
