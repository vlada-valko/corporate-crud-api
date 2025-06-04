package ua.corporate.avgust.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import ua.corporate.avgust.enums.Role;
import ua.corporate.avgust.model.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class UserDTO {
    private Long id;
    private Long employeeId;
    @NotEmpty(message = "Будь ласка, введіть ім'я користувача")
    @NotNull(message = "Будь ласка, введіть ім'я користувача")
    @Pattern(
            regexp = "^[A-Za-z0-9,._-]+$",
            message = "Ім'я користувача має бути латинськими літерами," +
                    " може містити цифри, коми, крапки, дефіс та нижнє підкреслення"
    )
    @JsonView(Views.UserViews.class)
    private String username;
    @NotEmpty(message = "Будь ласка, введіть пароль")
    @NotNull(message = "Будь ласка, введіть пароль")
    @Pattern(
            regexp = "^[A-Za-z0-9,._-]{4,}$",
            message = "Пароль користувача має бути латинськими літерами," +
                    " може містити цифри, коми, крапки, дефіс та нижнє підкреслення. " +
                    "Пароль має містити принаймні 4 символи."
    )
    @JsonView(Views.AdminViews.class)
    private String password;
    @NotEmpty(message = "Будь ласка, введіть пароль повторно")
    @NotNull(message = "Будь ласка, введіть пароль повторно")
    @JsonView(Views.AdminViews.class)
    private String confirmPassword;
    @Enumerated(value = EnumType.STRING)
    @JsonView(Views.AdminViews.class)
    private Role role;
    @JsonView(Views.ManagerViews.class)
    private LocalDateTime createdAt;
    @JsonView(Views.ManagerViews.class)
    private User createdBy;

}
