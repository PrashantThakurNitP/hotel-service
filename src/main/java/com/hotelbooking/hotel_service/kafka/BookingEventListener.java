package com.hotelbooking.hotel_service.kafka;

import com.hotelbooking.hotel_service.entity.Room;
import com.hotelbooking.hotel_service.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.hotelbooking.common.event.BookingEvent;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingEventListener {

    private final RoomRepository roomRepository;
    private final BookingConfirmationEventProducer eventProducer;

    @KafkaListener(topics = "booking-events", groupId = "hotel-service")
    public void handleBookingCreated(BookingEvent event) {
        log.info("Received booking event: {}", event);

        if (!"PENDING".equalsIgnoreCase(event.getStatus())) return;

        Optional<Room> roomOpt = roomRepository.findById(event.getRoomId());

        String finalStatus;
        if (roomOpt.isPresent() && Boolean.TRUE.equals(roomOpt.get().getAvailable())) {
            Room room = roomOpt.get();
            room.setAvailable(false); // mark as booked
            roomRepository.save(room);
            finalStatus = "CONFIRMED";
            log.info("Room {} marked as unavailable", room.getId());
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
