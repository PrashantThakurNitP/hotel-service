package com.hotelbooking.hotel_service.service;

import com.hotelbooking.hotel_service.dto.CreateHotelRequest;
import com.hotelbooking.hotel_service.entity.Hotel;
import com.hotelbooking.hotel_service.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;

    public Hotel createHotel(CreateHotelRequest createHotelRequest){
        Hotel hotel = Hotel.builder()
                .name(createHotelRequest.getName())
                .city(createHotelRequest.getCity())
                .address(createHotelRequest.getAddress())
                .description(createHotelRequest.getDescription())
                .rating(createHotelRequest.getRating())
                .build();
        return hotelRepository.save(hotel);
    }

    public Optional<Hotel> getHotelById(UUID id){
        return hotelRepository.findById(id);
    }

    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    public Hotel updateHotel(UUID id, CreateHotelRequest updateHotel){
       return hotelRepository.findById(id).map(hotel -> {
            hotel.setName(updateHotel.getName());
            hotel.setCity(updateHotel.getCity());
            hotel.setAddress(updateHotel.getAddress());
            hotel.setDescription(updateHotel.getDescription());
            hotel.setRating(updateHotel.getRating());
            return hotelRepository.save(hotel);
        }).orElseThrow(()->new RuntimeException("Hotel Not Found"));
    }
}
