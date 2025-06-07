package com.hotelbooking.hotel_service.service;

import com.hotelbooking.hotel_service.entity.Room;
import com.hotelbooking.hotel_service.entity.RoomAvailability;
import com.hotelbooking.hotel_service.repository.RoomAvailabilityRepository;
import com.hotelbooking.hotel_service.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomAvailabilityService {

    private final RoomAvailabilityRepository availabilityRepository;
    private final RoomRepository roomRepository;

    public boolean isAvailable(UUID roomId, LocalDate checkIn, LocalDate checkOut) {
        List<RoomAvailability> conflicts = availabilityRepository.findConflicts(roomId, checkIn, checkOut);
        return conflicts.isEmpty();
    }

    public void reserve(UUID roomId, LocalDate checkIn, LocalDate checkOut) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        RoomAvailability availability = RoomAvailability.builder()
                .room(room)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .booked(true)
                .build();

        availabilityRepository.save(availability);
    }

    public void cancelReservation(UUID roomId, LocalDate checkIn, LocalDate checkOut) {
        List<RoomAvailability> availabilities = availabilityRepository.findConflicts(roomId, checkIn, checkOut);
        for (RoomAvailability ra : availabilities) {
            ra.setBooked(false);
        }
        availabilityRepository.saveAll(availabilities);
    }
}
