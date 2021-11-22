package org.example.repository;

import org.example.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepo extends JpaRepository<Guest, Integer> {

}