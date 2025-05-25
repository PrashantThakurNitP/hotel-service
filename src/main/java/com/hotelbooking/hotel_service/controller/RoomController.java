package com.hotelbooking.hotel_service.controller;

import com.hotelbooking.hotel_service.dto.CreateRoomRequest;
import com.hotelbooking.hotel_service.entity.Room;
import com.hotelbooking.hotel_service.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Room>> getRoomsByHotel(@PathVariable UUID hotelId){
             return  ResponseEntity.ok(roomService.getRoomByHotelId(hotelId));
    }
    @PostMapping("/hotel/{hotelId}")
    public ResponseEntity<Room> createRoom(@PathVariable UUID hotelId, @Valid @RequestBody CreateRoomRequest request){
        return ResponseEntity.ok(roomService.createRoom(hotelId, request));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable UUID roomId){
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }
    @PutMapping("/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable UUID roomId, @RequestHeader UUID hotelId, @Valid @RequestBody CreateRoomRequest request){
        return ResponseEntity.ok(roomService.updateRoom(roomId, hotelId, request));
    }

}
