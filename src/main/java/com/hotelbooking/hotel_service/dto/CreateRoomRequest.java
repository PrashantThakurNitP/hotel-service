package com.hotelbooking.hotel_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "Request object for creating or updating a room")
public class CreateRoomRequest {

    private UUID hotelId;

    @NotBlank(message = "Room type is required")
    @Schema(description = "Type of the room (e.g., Deluxe, Suite)", example = "Deluxe")
    private String roomType;

    @NotNull(message = "Price is required")
    @Schema(description = "Price of the room per night", example = "1000.00")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Availability is required")
    @Schema(description = "Availability status of the room", example = "true")
    private Boolean available;
}
