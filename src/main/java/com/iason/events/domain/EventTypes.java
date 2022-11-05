package com.iason.events.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EventTypes {
    SPEED("speed"),
    RED_LIGHT("red_light");

    private String code;

    private EventTypes(String code) {

        this.code = code;
    }
    EventTypes() {
    }
}
