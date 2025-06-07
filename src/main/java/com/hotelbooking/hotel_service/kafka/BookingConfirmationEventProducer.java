package com.hotelbooking.hotel_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.hotelbooking.common.event.BookingEvent;
@Component
@RequiredArgsConstructor
@Slf4j
public class BookingConfirmationEventProducer {
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    public void send(BookingEvent event) {
        kafkaTemplate.send("booking-confirmation-events", event.getBookingId().toString(), event);
        log.info("Sent booking confirmation event: {}", event);
    }
}
