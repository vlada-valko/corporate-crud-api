package ua.corporate.avgust.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.corporate.avgust.api.response.ResponseFactory;
import ua.corporate.avgust.dto.UserDTO;
import ua.corporate.avgust.dto.WorkplaceTypeDTO;
import ua.corporate.avgust.service.WorkplaceTypeService;

import java.util.List;
import java.util.Map;
@RequiredArgsConstructor

@Controller
@RequestMapping("/workplace-types")
public class WorkplaceTypeController {
    //region standard fields
    private final WorkplaceTypeService workplaceTypeService;
    //endregion

    //CREATE
    @GetMapping("/new")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> createForm() {
        WorkplaceTypeDTO workplaceTypeDTO = new WorkplaceTypeDTO();
        Map<String, ?> createForm = Map.of(
                "workplaceTypeDTO", workplaceTypeDTO
        );
        return ResponseFactory.success(createForm, "create workplaceType form"
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> create(@Valid @RequestBody WorkplaceTypeDTO workplaceTypeDTO
    ) {
        WorkplaceTypeDTO createdWorkplaceType = workplaceTypeService.create(workplaceTypeDTO);
        return  ResponseFactory.created(createdWorkplaceType, "WorkplaceType successfully created");
    }

    //READ
    @GetMapping("/all")
    public ResponseEntity<?> readAll() {
        List<WorkplaceTypeDTO> workplaceTypeDTOList = workplaceTypeService.readAll();
        return ResponseFactory.success(workplaceTypeDTOList, "WorkplaceType successfully read");
    }

    @GetMapping("{id}")
    public ResponseEntity<?> readById(@PathVariable("id") Long id) {
        WorkplaceTypeDTO workplaceTypeDTO = workplaceTypeService
                .readById(id).orElseThrow(EntityNotFoundException::new);
        return ResponseFactory.success(workplaceTypeDTO, "WorkplaceType successfully read");
    }


    // UPDATE
    @GetMapping("{id}/edit")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> editForm(@PathVariable("id") Long id) {
        WorkplaceTypeDTO WorkplaceTypeEditForm = workplaceTypeService.readById(id)
                .orElseThrow(EntityNotFoundException::new);
        return ResponseFactory.success(WorkplaceTypeEditForm, "WorkplaceType edit form");
    }

    @PutMapping("{id}/edit")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> edit(@PathVariable("id") Long id, @Valid @RequestBody WorkplaceTypeDTO workplaceTypeDTO) {
        WorkplaceTypeDTO editedWorkplaceType = workplaceTypeService.update(id, workplaceTypeDTO);
        return ResponseFactory.success(editedWorkplaceType, "WorkplaceType successfully updated");
    }

    //DELETE
    @DeleteMapping("{id}/delete")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        workplaceTypeService.delete(id);
        return ResponseFactory.success(id, "WorkplaceType successfully deleted");
    }
}