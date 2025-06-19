package com.hotelbooking.hotel_service.service;

import com.hotelbooking.common.event.HotelEvent;
import com.hotelbooking.hotel_service.dto.CreateHotelRequest;
import com.hotelbooking.hotel_service.entity.Hotel;
import com.hotelbooking.hotel_service.kafka.SearchIndexEventProducer;
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
    private final SearchIndexEventProducer searchIndexEventProducer;

    public Hotel createHotel(CreateHotelRequest createHotelRequest){
        Hotel hotel = Hotel.builder()
                .name(createHotelRequest.getName())
                .city(createHotelRequest.getCity())
                .address(createHotelRequest.getAddress())
                .description(createHotelRequest.getDescription())
                .rating(createHotelRequest.getRating())
                .build();

        Hotel hotelResponse =  hotelRepository.save(hotel);
        searchIndexEventProducer.publishHotelEvent(mapToHotelEvent(hotel));
        return hotelResponse;
    }

    public HotelEvent mapToHotelEvent(Hotel hotelDocument) {
        return new HotelEvent(
                hotelDocument.getId(),
                hotelDocument.getName(),
                hotelDocument.getCity(),
                hotelDocument.getRating()
        );
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
            searchIndexEventProducer.publishHotelEvent(mapToHotelEvent(hotel));
            return hotelRepository.save(hotel);
        }).orElseThrow(()->new RuntimeException("Hotel Not Found"));
    }
}
