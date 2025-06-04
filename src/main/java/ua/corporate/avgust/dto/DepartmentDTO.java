package ua.corporate.avgust.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import ua.corporate.avgust.model.Employee;
import ua.corporate.avgust.model.Position;
import ua.corporate.avgust.model.User;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class DepartmentDTO {
    Long id;
    @NotNull(message = "Вкажіть назву департаменту")
    @NotEmpty(message = "Вкажіть назву департаменту")
    @Pattern(
            regexp = "^[А-ЯЇЄІҐA-Z][A-Zа-яїєіґ'’-]*(\\s[а-яїєіґ'’-]*)*$",
            message = "Назва департаменту" +
                    " має починатися з великої літери, " +
                    "містити лише українські літери, може містити апостроф або дефіс"
    )
    private String name;
    @JsonManagedReference
    private List<EmployeeDTO> employees;
    @JsonManagedReference
    private List<PositionDTO> positions;
    @JsonManagedReference
    private EmployeeDTO manager;
    @JsonIgnore
    @JsonManagedReference
    private UserDTO createdBy;
}
