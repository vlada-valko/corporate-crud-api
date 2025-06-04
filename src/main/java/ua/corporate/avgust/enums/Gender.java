package ua.corporate.avgust.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("Ч"), FEMALE("Ж");

    private final String description;

    Gender(String description) {
        this.description = description;
    }
@JsonValue
    public String getDescription() {
        return description;
    }
}
