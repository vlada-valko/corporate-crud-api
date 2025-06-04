package ua.corporate.avgust.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ua.corporate.avgust.model.Employee;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class WorkplaceTypeDTO {
    private Long id;
    @NotEmpty(message = "Введіть назву локації/тип роботи")
    @NotNull(message = "Введіть назву локації/тип роботи")
    private String name;
    List<Employee> employees;
}
