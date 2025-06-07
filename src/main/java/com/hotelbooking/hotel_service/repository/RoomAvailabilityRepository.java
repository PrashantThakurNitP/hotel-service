package com.hotelbooking.hotel_service.repository;

import com.hotelbooking.hotel_service.entity.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, UUID> {
    @Query("SELECT ra FROM RoomAvailability ra WHERE ra.room.id = :roomId AND ra.booked = true AND " +
            "((:checkIn BETWEEN ra.checkIn AND ra.checkOut) OR " +
            "(:checkOut BETWEEN ra.checkIn AND ra.checkOut) OR " +
            "(ra.checkIn BETWEEN :checkIn AND :checkOut))")
    List<RoomAvailability> findConflicts(UUID roomId, LocalDate checkIn, LocalDate checkOut);
}
