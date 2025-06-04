package ua.corporate.avgust.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.corporate.avgust.api.response.ResponseFactory;
import ua.corporate.avgust.dto.DepartmentDTO;
import ua.corporate.avgust.service.DepartmentService;
import ua.corporate.avgust.service.EmployeeService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    //region standard fields
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    //endregion

    //CREATE
    @GetMapping("/new")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> createForm() {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        List<String> managers = employeeService.readManagers().stream()
                .map(e -> String.format("%s %s %s",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getMiddleName()))
                .collect(Collectors.toList());
        Map<String, ?> createForm = Map.of(
                "departmentDTO", departmentDTO,
                "managers", managers
        );
        return ResponseFactory.success(createForm, "create department form"
                );
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> create(@Valid @RequestBody DepartmentDTO departmentDTO
    ) {
        DepartmentDTO createdDepartment = departmentService.create(departmentDTO);
        return  ResponseFactory.created(createdDepartment, "department successfully created");
    }

    //READ
    @GetMapping("/all")
    public ResponseEntity<?> readAll() {
        List<DepartmentDTO> departmentDTOList = departmentService.readAll();
        return ResponseFactory.success(departmentDTOList, "departments successfully read");
    }

    @GetMapping("{id}")
    public ResponseEntity<?> readById(@PathVariable("id") Long id) {
        DepartmentDTO departmentDTO = departmentService
                .readById(id).orElseThrow(EntityNotFoundException::new);
        return ResponseFactory.success(departmentDTO, "department successfully read");
    }


    // UPDATE
    @GetMapping("{id}/edit")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> editForm(@PathVariable("id") Long id) {
        DepartmentDTO departmentEditForm = departmentService.readById(id).orElseThrow(EntityNotFoundException::new);
        return ResponseFactory.success(departmentEditForm, "department edit form");
    }

    @PutMapping("{id}/edit")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> edit(@PathVariable("id") Long id, @Valid @RequestBody DepartmentDTO departmentDTO) {
      DepartmentDTO editedDepartment = departmentService.update(id, departmentDTO);
      return ResponseFactory.success(editedDepartment, "department successfully updated");
    }

    //DELETE
    @DeleteMapping("{id}/delete")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        departmentService.delete(id);
       return ResponseFactory.success(id, "department successfully deleted");
    }
}
