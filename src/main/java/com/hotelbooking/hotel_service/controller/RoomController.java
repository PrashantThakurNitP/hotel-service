package com.hotelbooking.hotel_service.controller;

import com.hotelbooking.hotel_service.dto.CreateRoomRequest;
import com.hotelbooking.hotel_service.entity.Room;
import com.hotelbooking.hotel_service.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Room Controller", description = "APIs for managing hotel rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @Operation(summary = "Get rooms by hotel ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rooms retrieved successfully")
    })
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Room>> getRoomsByHotel(@PathVariable UUID hotelId){
             return  ResponseEntity.ok(roomService.getRoomByHotelId(hotelId));
    }

    @Operation(summary = "Create a room for a hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid room data")
    })
    @PostMapping("/hotel/{hotelId}")
    @PreAuthorize("hasRole('HOTEL_OWNER') or hasRole('ADMIN')")
    public ResponseEntity<Room> createRoom(@PathVariable UUID hotelId, @Valid @RequestBody CreateRoomRequest request , @AuthenticationPrincipal String userId){
        request.setOwnerId((UUID.fromString(userId)));
        return ResponseEntity.ok(roomService.createRoom(hotelId, request));
    }

    @Operation(summary = "Get room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room found"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable UUID roomId){
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @Operation(summary = "Update a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data or missing hotel ID"),
            @ApiResponse(responseCode = "404", description = "Room or Hotel not found")
    })
    @PutMapping("/{roomId}")
    @PreAuthorize("hasRole('HOTEL_OWNER') or hasRole('ADMIN')")
    public ResponseEntity<Room> updateRoom(@PathVariable UUID roomId, @RequestHeader UUID hotelId, @Valid @RequestBody CreateRoomRequest request , @AuthenticationPrincipal String userId){
        request.setOwnerId((UUID.fromString(userId)));
        return ResponseEntity.ok(roomService.updateRoom(roomId, hotelId, request));
    }

}
