package com.hotelbooking.hotel_service.service;

import com.hotelbooking.hotel_service.dto.CreateRoomRequest;
import com.hotelbooking.hotel_service.entity.Hotel;
import com.hotelbooking.hotel_service.entity.Room;
import com.hotelbooking.hotel_service.repository.HotelRepository;
import com.hotelbooking.hotel_service.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public  List<Room> getRoomByHotelId(UUID hotelId){
       return roomRepository.findByHotelId(hotelId);
    }

    public Room createRoom(UUID hotelId, CreateRoomRequest createRoomRequest){
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        Room room = Room.builder()
                .hotel(hotel)
                .roomType(createRoomRequest.getRoomType())
                .price(createRoomRequest.getPrice())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return roomRepository.save(room);
    }
    public Room getRoomById(UUID id){
       return roomRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Room Not Found"));
    }

    public Room updateRoom(UUID roomId, UUID hotelId, CreateRoomRequest roomRequest){
        Room room = getRoomById(roomId);
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()->new EntityNotFoundException("Hotel Not Found"));
        if(!room.getHotel().getId().equals(hotel.getId())){
            throw new EntityNotFoundException("Room don't exist in Hotel");
        }
        room.setRoomType(roomRequest.getRoomType());
        room.setPrice(roomRequest.getPrice());

        return roomRepository.save(room);
    }
}
