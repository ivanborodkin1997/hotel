package org.example.repository;

import org.example.model.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRoomRepo extends JpaRepository<HotelRoom, Integer> {

}