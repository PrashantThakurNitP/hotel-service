package com.hotelbooking.hotel_service.kafka;

import com.hotelbooking.common.event.HotelEvent;
import com.hotelbooking.common.event.RoomEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SearchIndexEventProducer {


    private final KafkaTemplate<String, HotelEvent> kafkaHotelTemplate;
    private final KafkaTemplate<String, RoomEvent> kafkaRoomTemplate;


    public SearchIndexEventProducer(@Qualifier("hotelIndexEventKafkaTemplate") KafkaTemplate<String, HotelEvent> kafkaHotelTemplate, @Qualifier("roomIndexEventKafkaTemplate") KafkaTemplate<String, RoomEvent> kafkaRoomTemplate) {
        this.kafkaHotelTemplate = kafkaHotelTemplate;
        this.kafkaRoomTemplate = kafkaRoomTemplate;
    }

    public void publishHotelEvent(HotelEvent event) {
        String topic = "hotel-events";
        log.info("Publishing hotel event on topic {} {} ", topic, event.getHotelId());
        kafkaHotelTemplate.send(topic, event.getHotelId().toString(), event);
    }

    public void publishRoomEvent(RoomEvent event) {
        String topic = "room-events";
        log.info("Publishing room event on topic {} {} ", topic, event.getRoomId());
        kafkaRoomTemplate.send(topic, event.getRoomId().toString(), event);
    }
}
