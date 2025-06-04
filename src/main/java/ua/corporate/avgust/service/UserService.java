package ua.corporate.avgust.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.corporate.avgust.dto.UserDTO;
import ua.corporate.avgust.dto.Views;
import ua.corporate.avgust.enums.Role;
import ua.corporate.avgust.mapper.UserMapper;
import ua.corporate.avgust.model.Employee;
import ua.corporate.avgust.model.User;
import ua.corporate.avgust.model.User;
import ua.corporate.avgust.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {
    //region standard fields
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    //endregion standard fields

    //CREATE
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.toModel(userDTO)
                .toBuilder()
                .createdAt(LocalDateTime.now())
                .createdBy(getCurrentUser())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        if (user.getRole() == null) {
            user.setRole(Role.ROLE_USER);
        }
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    //READ
    public List<UserDTO> readAll() {
                return userRepository.findAll().stream()
                        .map(userMapper::toDTO)
                        .collect(Collectors.toList());
    }

    public Optional<UserDTO> readById(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    public Optional<UserDTO> readByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::toDTO);
    }

    public List<UserDTO> readUsersWithoutEmployee() {
        return userRepository.findUsersWithoutEmployee().stream()
                .map(userMapper::toDTO).collect(Collectors.toList());
    }


    //UPDATE
    @Transactional
    public UserDTO update(Long id, UserDTO updatedUserDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userMapper.updateModelFromDto(updatedUserDTO, existingUser);
        existingUser.setUpdatedAt(LocalDateTime.now());
        existingUser.setUpdatedBy(getCurrentUser());
        userRepository.save(existingUser);
        return userMapper.toDTO(existingUser);
    }

    //DELETE
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }

    public Class<?> determineViewForRole() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.printf(currentUsername + "/n");
        User currentUser = userRepository.findByUsername(currentUsername).orElse(null);
        if (currentUser == null) {
            System.out.printf("currentUser == null");
            return Views.UserViews.class;
        }

        switch (currentUser.getRole()) {
            case ROLE_ADMIN:
                System.out.printf("Views.AdminViews.class");
                return Views.AdminViews.class;
            case ROLE_MANAGER:
                System.out.printf("Views.ManagerViews.class");
                return Views.ManagerViews.class;
            default:
                System.out.printf("default");
                return Views.UserViews.class;
        }
    }

    public User getCurrentUser() {
        return userRepository.findByUsername(SecurityContextHolder.getContext()
                        .getAuthentication().getName())
                .orElse(null);
    }

}
