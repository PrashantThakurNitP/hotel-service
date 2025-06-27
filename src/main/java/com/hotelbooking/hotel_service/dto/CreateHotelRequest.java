package com.hotelbooking.hotel_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Request object for creating or updating a hotel")
public class CreateHotelRequest {

    @NotBlank(message = "Hotel name is required")
    @Size(min = 2, max = 50, message = "City name must be between 2 and 50 characters")
    @Schema(description = "Name of the hotel", example = "Hotel Paradise")
    private String name;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 50, message = "Hotel name  must be between 2 and 50 characters")
    @Schema(description = "City where the hotel is located", example = "New Delhi")
    private String city;

    @NotBlank(message = "Address is required")
    @Schema(description = "Full address of the hotel", example = "123 MG Road, Connaught Place")
    private String address;

    @NotBlank(message = "Description is required")
    @Schema(description = "Brief description of the hotel", example = "A 5-star hotel with luxury amenities")
    private String description;

    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be at least 0.0")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating must not exceed 5.0")
    @Schema(description = "Average rating of the hotel (0.0 to 5.0)", example = "4.5")
    private Double rating;

    private UUID ownerId; // This stores the hotel owner's user ID
}
