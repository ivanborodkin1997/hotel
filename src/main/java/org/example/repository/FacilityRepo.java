package org.example.repository;

import org.example.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepo extends JpaRepository<Facility, Integer> {

}