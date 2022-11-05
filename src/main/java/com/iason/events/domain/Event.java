package com.iason.events.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Event {

    private UUID id;

    private LocalDateTime date;

    private String type;

    private String licensePlate;

    private double speed;

    private double limit;

    private String unit;

    private boolean processed;

}
