package com.hotelbooking.hotel_service.config;

import com.hotelbooking.common.event.BookingEvent;
import com.hotelbooking.common.event.RoomEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.hotelbooking.common.event.HotelEvent;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public ProducerFactory<String, BookingEvent> bookingConfirmationProducerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(config);
  }

  @Bean
  public ProducerFactory<String, HotelEvent> hotelIndexProducerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(config);
  }

  @Bean
  public ProducerFactory<String, RoomEvent> roomIndexProducerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(config);
  }

  @Bean(name = "hotelIndexEventKafkaTemplate")
  public KafkaTemplate<String, HotelEvent> hotelIndexEventKafkaTemplate() {
    return new KafkaTemplate<>(hotelIndexProducerFactory());
  }

  @Bean(name = "roomIndexEventKafkaTemplate")
  public KafkaTemplate<String, RoomEvent> roomIndexEventKafkaTemplate() {
    return new KafkaTemplate<>(roomIndexProducerFactory());
  }

  @Bean(name = "bookingConfirmationEventKafkaTemplate")
  public KafkaTemplate<String, BookingEvent> bookingConfirmationEventKafkaTemplate() {
    return new KafkaTemplate<>(bookingConfirmationProducerFactory());
  }
}
