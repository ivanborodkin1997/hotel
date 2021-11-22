package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.FacilityDto;
import org.example.exception.EmptyInputException;
import org.example.exception.EmptyListException;
import org.example.exception.NoSuchFacilityException;
import org.example.mapper.FacilityMapper;
import org.example.model.Facility;
import org.example.model.SortingMethod;
import org.example.repository.FacilityRepo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepo facilityRepo;
    private final FacilityMapper facilityMapper;

    @Override
    public List<FacilityDto> getAllFacility() {
        List<Facility> facilitiesList = facilityRepo.findAll();
        if (facilitiesList.isEmpty()) {
            throw new EmptyListException();
        }
        return facilityMapper.toDtos(facilitiesList);
    }

    @Override
    public FacilityDto getFacility(Integer id) {
        return facilityMapper.toDto(facilityRepo.findById(id).orElseThrow(() -> new NoSuchFacilityException(id)));
    }

    @Override
    public FacilityDto saveFacility(FacilityDto facilityDto) {
        if (facilityDto.getFacilityName().isEmpty() || facilityDto.getFacilityCategory().isEmpty() ||
                facilityDto.getFacilityPrice() == null) {
            throw new EmptyInputException();
        }
        Facility facility = facilityRepo.save(facilityMapper.fromDto(facilityDto));
        return facilityMapper.toDto(facility);
    }

    @Override
    public void deleteFacility(Integer id) {
        try {
            facilityRepo.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NoSuchFacilityException(id);
        }
    }

    @Override
    public List<FacilityDto> getPricesForAllFacilities(SortingMethod sortingMethod) {
        List<FacilityDto> facilitiesList = facilityMapper.toDtos(facilityRepo.findAll());
        if (facilitiesList.isEmpty()) {
            throw new EmptyListException();
        }

        return sortFacility(facilitiesList, sortingMethod);
    }

    private List<FacilityDto> sortFacility(List<FacilityDto> facilitiesList, SortingMethod sortingMethod) {
        switch (sortingMethod) {
            case BY_PRICE:
                return facilitiesList.stream().sorted(Comparator.comparingDouble(FacilityDto::getFacilityPrice)).collect(Collectors.toList());
            case BY_DATE:
                return facilitiesList.stream().sorted(Comparator.comparing(FacilityDto::getFacilityDateTime)).collect(Collectors.toList());
            case BY_CATEGORY:
                return facilitiesList.stream().sorted(Comparator.comparing(FacilityDto::getFacilityCategory)).collect(Collectors.toList());
        }
        return null;
    }

}
