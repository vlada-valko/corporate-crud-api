package ua.corporate.avgust.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import ua.corporate.avgust.model.Department;
import ua.corporate.avgust.model.Employee;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class PositionDTO {
    private Long id;
    @NotEmpty(message = "Будь ласка, введіть назву посади")
    @NotNull(message = "Будь ласка, введіть назву посади")
    @Pattern(
            regexp = "^[А-ЯЇЄІҐ][а-яїєіґ'’-]*(?: [а-яїєіґ'’-]*)*$",
            message = "Посада має починатися з великої літери, містити лише українські літери," +
                    "може містити апостроф або дефіс"
    )
    private String name;
    private List<Employee> employees;
    @JsonBackReference
    private Department department;
}
