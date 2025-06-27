package com.hotelbooking.hotel_service.controller;

import com.hotelbooking.hotel_service.dto.CreateHotelRequest;
import com.hotelbooking.hotel_service.entity.Hotel;
import com.hotelbooking.hotel_service.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hotels")
@Tag(name = "Hotel Controller", description = "APIs for managing hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {
    private final HotelService hotelService;


    @Operation(summary = "Create a new hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PreAuthorize("hasRole('HOTEL_OWNER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody CreateHotelRequest createHotelRequest, @AuthenticationPrincipal String userId){
        log.info("Request to create hotel {} for owner {}", createHotelRequest.getName(), userId);
        createHotelRequest.setOwnerId((UUID.fromString(userId)));
        return ResponseEntity.ok(hotelService.createHotel(createHotelRequest));
    }


    @Operation(summary = "Get hotel by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel found"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable UUID hotelId){
       return hotelService.getHotelById(hotelId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Get all hotels")
    @ApiResponse(responseCode = "200", description = "List of all hotels")
    @GetMapping
    public List<Hotel> getAllHotels(){
        return hotelService.getAllHotels();

    }

    @Operation(summary = "Update a hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HOTEL_OWNER') or hasRole('ADMIN')")
    public ResponseEntity<Hotel> updateHotel(@PathVariable UUID id, @Valid @RequestBody CreateHotelRequest request, @AuthenticationPrincipal String userId) {
        request.setOwnerId((UUID.fromString(userId)));
        return ResponseEntity.ok(hotelService.updateHotel(id, request));
    }
}
