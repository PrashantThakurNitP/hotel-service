package com.hotelbooking.hotel_service.controller;

import com.hotelbooking.hotel_service.dto.CreateHotelRequest;
import com.hotelbooking.hotel_service.entity.Hotel;
import com.hotelbooking.hotel_service.service.HotelService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody CreateHotelRequest createHotelRequest){
        return ResponseEntity.ok(hotelService.createHotel(createHotelRequest));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable UUID hotelId){
       return hotelService.getHotelById(hotelId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Hotel> getAllHotels(){
        return hotelService.getAllHotels();

    }
    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable UUID id, @Valid @RequestBody CreateHotelRequest request) {
        return ResponseEntity.ok(hotelService.updateHotel(id, request));
    }
}
