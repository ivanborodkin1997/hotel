package org.example.service;

import org.example.dto.FacilityDto;
import org.example.model.Facility;
import org.example.model.SortingMethod;

import java.util.List;

public interface FacilityService {

    List<FacilityDto> getAllFacility();

    FacilityDto saveFacility(FacilityDto facilityDto);

    void deleteFacility(Integer id);

    List<FacilityDto> getPricesForAllFacilities(SortingMethod sortingMethod);

    FacilityDto getFacility(Integer id);

}
