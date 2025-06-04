package ua.corporate.avgust.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ROLE_USER("Користувач"),
    ROLE_MANAGER("Менеджер"),
    ROLE_ADMIN("Адміністратор");
    private final String description;

    Role(String description) {
        this.description = description;
    }
    @JsonValue
    public String getDescription() {
        return description;
    }

}
