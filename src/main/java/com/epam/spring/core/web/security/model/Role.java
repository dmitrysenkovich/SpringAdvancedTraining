package com.epam.spring.core.web.security.model;

public enum Role {
    REGISTERED_USER("REGISTERED_USER"),
    BOOKING_MANAGER("BOOKING_MANAGER");

    private final String text;

    Role(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
