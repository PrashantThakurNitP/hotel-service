package com.hotelbooking.hotel_service.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.hotelbooking.common.event.BookingEvent;
@Component
@Slf4j
public class BookingConfirmationEventProducer {

    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;
    public BookingConfirmationEventProducer(@Qualifier("bookingConfirmationEventKafkaTemplate") KafkaTemplate<String, BookingEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(BookingEvent event) {
        kafkaTemplate.send("booking-confirmation-events", event.getBookingId().toString(), event);
        log.info("Sent booking confirmation event: {}", event);
    }
}
