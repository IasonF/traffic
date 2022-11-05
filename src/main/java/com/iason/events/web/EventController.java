package com.iason.events.web;

import com.iason.events.domain.CreateEventRequest;
import com.iason.events.domain.Event;
import com.iason.events.services.EventService;
import com.iason.events.services.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@Validated
@RequestMapping(path = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private ViolationService violationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Event createEvent(@Valid @RequestBody CreateEventRequest request) {
        return eventService.createEvent(request);
    }

    @GetMapping("/violations")
    Set<UUID> getViolations() {
        return violationService.getViolations();
    }

    @PutMapping("/violation/{id}")
    @ResponseStatus(HttpStatus.OK)
    void payViolation(@PathVariable("id") final UUID id) {
        violationService.payViolation(id);
    }

    @GetMapping("/violations/summary")
    String getSummary() {
        return violationService.getSummary();
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("Validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

