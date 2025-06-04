package ua.corporate.avgust.controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.corporate.avgust.api.response.ResponseFactory;
import ua.corporate.avgust.dto.EmployeeDTO;
import ua.corporate.avgust.enums.Gender;
import ua.corporate.avgust.enums.Role;
import ua.corporate.avgust.mapper.DepartmentMapper;
import ua.corporate.avgust.mapper.EmployeeMapper;
import ua.corporate.avgust.service.*;
import ua.corporate.avgust.validator.EmployeeDTOValidator;
import ua.corporate.avgust.validator.UserDTOValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    //region standard fields
    public final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final PositionService positionService;
    private final WorkplaceTypeService workplaceTypeService;
    private final UserService userService;
    //endregion

    @GetMapping("/new")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> createForm() {
        Map<String, Object> createForm = new HashMap<>();
        createForm.put("createEmployeeForm", new EmployeeDTO());
        createForm.put("departments", departmentService.readAll().stream()
                .map(department -> Map.of(
                        "id", department.getId(),
                        "name", department.getName()))
                .collect(Collectors.toList()));
        createForm.put("positions", positionService.readAll().stream()
                .map(position -> Map.of(
                        "id", position.getId(),
                        "name", position.getName()))
                .collect(Collectors.toList()));
        createForm.put("workplaceTypes", workplaceTypeService.readAll().stream()
                .map(type -> Map.of(
                        "id", type.getId(),
                        "name", type.getName()))
                .collect(Collectors.toList()));
        createForm.put("genders", Gender.values());
        createForm.put("roles", Role.values());
        createForm.put("users", userService.readUsersWithoutEmployee().stream()
                .map(user -> Map.of(
                        "id", user.getId(),
                        "username", user.getUsername()))
                .collect(Collectors.toList()));

        return ResponseFactory.success(createForm,"create employee form");
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> create(
            @Valid @RequestBody EmployeeDTO employeeDTO
    ) {
        EmployeeDTO createdEmployee = employeeService.create(employeeDTO);
            return ResponseFactory.created(createdEmployee,"employee successfully created");
    }


    @GetMapping("/all")
    public ResponseEntity<?> readAll() {
        List<EmployeeDTO> employeeDTOList = employeeService.readAll();
        return ResponseFactory.success(employeeDTOList,"read all employees");
    }

    @GetMapping("/get-employee-count")
    public ResponseEntity<?> getEmployeeCount() {
        Map<String, Integer> employeeCount = new HashMap<>();
        employeeCount.put("employeeCount", employeeService.readAll().size());
        return ResponseFactory.success(employeeCount,"get employee count");
    }

    @GetMapping("{id}")
    public ResponseEntity<?> readById(@PathVariable("id") Long id) {
        EmployeeDTO employee = employeeService.readById(id).orElseThrow(EntityNotFoundException::new);
        return ResponseFactory.success(employee, "employee successfully read");
    }

    @GetMapping("{id}/edit")
    @PreAuthorize("hasRole('MANAGER') or #userId == authentication.principal.id")
    public ResponseEntity<?> edit(@PathVariable("id") Long id) {
        EmployeeDTO employeeEditForm = employeeService.readById(id).orElseThrow(EntityNotFoundException::new);
        return ResponseFactory.success(employeeEditForm, "employee edit form");
    }

    @PatchMapping("/{id}/edit")
    @PreAuthorize("hasRole('MANAGER') or #userId == authentication.principal.id")
    public ResponseEntity<?> update(
            @Valid @RequestBody EmployeeDTO employeeDTO,
            @PathVariable("id") Long userId
    ) {
        EmployeeDTO editedEmployee = employeeService.update(userId, employeeDTO);
        return ResponseFactory.success(editedEmployee, "employee successfully updated");
    }

    @DeleteMapping("{id}/delete")
    @PreAuthorize("hasRole('MANAGER') or #userId == authentication.principal.id")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return ResponseFactory.success(id, "employee successfully deleted");
    }


}
