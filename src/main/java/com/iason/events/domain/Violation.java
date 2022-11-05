package com.iason.events.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Violation {

    public static final int SPEED_FINE = 50;
    public static final int RED_LIGHT_FINE = 100;

    public UUID id;

    public UUID eventId;

    public double fine;

    public boolean paid;
}
