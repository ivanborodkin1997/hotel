package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.FacilityDto;
import org.example.model.Facility;
import org.example.model.SortingMethod;
import org.example.service.FacilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<FacilityDto>> getAllFacilities() {
        return ResponseEntity.ok(facilityService.getAllFacility());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<FacilityDto> getFacility(@PathVariable int id) {
        return ResponseEntity.ok(facilityService.getFacility(id));
    }

    @GetMapping("/all-facility-sort")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<FacilityDto>> getAllFacilitySortBy(@RequestParam SortingMethod sortingMethod) {
        return ResponseEntity.ok(facilityService.getPricesForAllFacilities(sortingMethod));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<FacilityDto> addFacility(@RequestBody FacilityDto facilityDto) {
        return ResponseEntity.ok(facilityService.saveFacility(facilityDto));
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<FacilityDto> updateFacility(@RequestBody FacilityDto facilityDto) {
        return ResponseEntity.ok(facilityService.saveFacility(facilityDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<String> deleteFacility(@PathVariable int id) {
        facilityService.deleteFacility(id);
        return ResponseEntity.ok("Facility with ID = " + id + " was deleted");
    }

}
