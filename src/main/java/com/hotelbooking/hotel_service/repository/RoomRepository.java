package com.hotelbooking.hotel_service.repository;

import com.hotelbooking.hotel_service.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
  List<Room> findByHotelId(UUID hotelId);
}
