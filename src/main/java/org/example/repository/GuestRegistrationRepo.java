package org.example.repository;

import org.example.model.GuestRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRegistrationRepo extends JpaRepository<GuestRegistration, Integer> {

    List<GuestRegistration> findAllByOrderByCheckOutDateAsc();

}
