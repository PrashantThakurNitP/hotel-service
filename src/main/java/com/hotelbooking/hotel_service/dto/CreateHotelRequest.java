package com.hotelbooking.hotel_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateHotelRequest {

    @NotBlank(message = "Hotel name is required")
    @Size(min = 2, max = 50, message = "City name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 50, message = "Hotel name  must be between 2 and 50 characters")
    private String city;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be at least 0.0")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating must not exceed 5.0")
    private Double rating;
}
