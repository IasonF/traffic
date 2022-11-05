package com.iason.events.services;

import com.iason.events.domain.CreateEventRequest;
import com.iason.events.domain.Event;
import com.iason.events.domain.Violation;
import lombok.Data;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Component
public class EventService {

    private final ApplicationEventPublisher publisher;

    public Event createEvent(CreateEventRequest request) {
        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setDate(LocalDateTime.now());
        event.setSpeed(request.speed);
        event.setLimit(request.limit);
        event.setType(request.type);
        event.setUnit(request.unity);
        event.setProcessed(false);
        event.setLicensePlate(request.licensePlate);
        publisher.publishEvent(event);
        return event;
    }


}
