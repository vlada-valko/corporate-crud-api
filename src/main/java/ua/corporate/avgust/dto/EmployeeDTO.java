package ua.corporate.avgust.dto;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import ua.corporate.avgust.enums.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class EmployeeDTO {
    private Long id;
    private Long userId;
    //region NAME
    @NotEmpty(message = "Будь ласка, введіть прізвище.")
    @NotNull
    @Pattern(
            regexp = "^[А-ЯЇЄІҐ][а-яїєіґ'’-]+$",
            message = "Прізвище має починатися з великої літери, містити лише українські літери, " +
                    "може містити апостроф або дефіс."
    )
    @JsonView(value = Views.UserViews.class)
    private String lastName;
    @NotEmpty(message = "Будь ласка, введіть ім'я.")
    @NotNull
    @Pattern(
            regexp = "^[А-ЯЇЄІҐ][а-яїєіґ'’-]+$",
            message = "Ім'я має починатися з великої літери, містити лише українські літери,може містити апостроф або дефіс."
    )
    @JsonView(Views.UserViews.class)
    private String firstName;
    @Pattern(
            regexp = "^[А-ЯЇЄІҐ][а-яїєіґ'’-]+$",
            message = "По батькові має починатися з великої літери, містити лише українські літери,може містити апостроф або дефіс."
    )
    @JsonView(Views.UserViews.class)
    private String middleName;
    //endregion
    //region WORK_INFO
    @JsonView(Views.UserViews.class)
    private Long departmentId;
    @JsonView(Views.UserViews.class)
    private Long managerId;
    @JsonView(Views.UserViews.class)
    private Long positionId;
    private LocalDate employmentStartDate;
    @JsonView(Views.UserViews.class)
    private Long workplaceTypeId;
    //endregion
    //region CONTACTS
    @Pattern(regexp = "^$|^0\\d{9}$", message = "Введіть номер телефону у форматі 0(99)-999-99-99")
    @JsonView(Views.UserViews.class)
    private String corporateMobile;
    @Pattern(
            regexp = "^0\\d{9}$",
            message = "Введіть номер телефону у форматі 0(99)-999-99-99"
    )
    @JsonView(Views.UserViews.class)
    private String personalMobile;
    @JsonView(Views.UserViews.class)
    private String internalPhone;
    @Email
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Введіть валіду пошту"
    )
    @JsonView(Views.UserViews.class)
    private String personalEmail;
    @Email
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Введіть валіду пошту"
    )
    @JsonView(Views.UserViews.class)
    private String workEmail;
    @JsonView(Views.ManagerViews.class)
    private String residentialAddress;
    //endregion
    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Будь ласка, оберіть стать")
    @JsonView(Views.UserViews.class)
    private Gender gender;
    @JsonView(Views.UserViews.class)
    private LocalDate dateOfBirth;
    @JsonView(Views.UserViews.class)
    private byte[] photo;



}
