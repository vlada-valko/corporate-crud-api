package ua.corporate.avgust.controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.corporate.avgust.api.response.ResponseFactory;
import ua.corporate.avgust.dto.UserDTO;
import ua.corporate.avgust.service.UserService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    //region standard fields
    private final UserService userService;
    //endregion

    //CREATE
    @GetMapping("/new")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> createForm() {
        UserDTO userDTO = new UserDTO();
        Map<String, ?> createForm = Map.of(
                "userDTO", userDTO
        );
        return ResponseFactory.success(createForm, "create user form"
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> create(@Valid @RequestBody UserDTO userDTO
    ) {
        UserDTO createdUser = userService.create(userDTO);
        return  ResponseFactory.created(createdUser, "user successfully created");
    }

    //READ
    @GetMapping("/all")
    public ResponseEntity<?> readAll() {
        List<UserDTO> userDTOList = userService.readAll();
        return ResponseFactory.success(userDTOList, "users successfully read");
    }

    @GetMapping("{id}")
    public ResponseEntity<?> readById(@PathVariable("id") Long id) {
        UserDTO userDTO = userService
                .readById(id).orElseThrow(EntityNotFoundException::new);
        return ResponseFactory.success(userDTO, "user successfully read");
    }


    // UPDATE
    @GetMapping("{id}/edit")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> editForm(@PathVariable("id") Long id) {
        UserDTO userEditForm = userService.readById(id).orElseThrow(EntityNotFoundException::new);
        return ResponseFactory.success(userEditForm, "user edit form");
    }

    @PutMapping("{id}/edit")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> edit(@PathVariable("id") Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO editedUser = userService.update(id, userDTO);
        return ResponseFactory.success(editedUser, "user successfully updated");
    }

    //DELETE
    @DeleteMapping("{id}/delete")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseFactory.success(id, "user successfully deleted");
    }
}