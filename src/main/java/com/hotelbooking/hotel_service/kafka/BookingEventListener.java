package com.hotelbooking.hotel_service.kafka;

import com.hotelbooking.hotel_service.service.RoomAvailabilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.hotelbooking.common.event.BookingEvent;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingEventListener {

    private final RoomAvailabilityService roomAvailabilityService;
    private final BookingConfirmationEventProducer eventProducer;

    @KafkaListener(topics = "booking-events", groupId = "hotel-service")
    public void handleBookingCreated(BookingEvent event) {
        log.info("Received booking event: {}", event);

        if (!"PENDING".equalsIgnoreCase(event.getStatus())) {
            log.info("Skipping non-pending event: {}", event.getStatus());
            return;
        }

        UUID roomId = event.getRoomId();
        LocalDate checkIn = event.getCheckIn();
        LocalDate checkOut = event.getCheckOut();

        boolean available = roomAvailabilityService.isAvailable(roomId, checkIn, checkOut);



        String finalStatus;
        if (available) {
            roomAvailabilityService.reserve(roomId, checkIn, checkOut);
            finalStatus = "CONFIRMED";
            log.info("Room {} marked as unavailable", roomId);
        } else {
            finalStatus = "REJECTED";
            log.info("Room {} unavailable or not found", event.getRoomId());
        }

        BookingEvent confirmationEvent = event.toBuilder()
                .status(finalStatus)
                .build();

        eventProducer.send(confirmationEvent);
    }

}
