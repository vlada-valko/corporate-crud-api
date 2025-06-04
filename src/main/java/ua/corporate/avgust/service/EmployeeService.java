package ua.corporate.avgust.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.corporate.avgust.dto.EmployeeDTO;
import ua.corporate.avgust.mapper.EmployeeMapper;
import ua.corporate.avgust.model.Employee;
import ua.corporate.avgust.repository.EmployeeRepository;
import ua.corporate.avgust.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final EmployeeMapper employeeMapper;


    //CREATE
    @Transactional
    public EmployeeDTO create(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toModel(employeeDTO)
                .toBuilder()
                .createdAt(LocalDateTime.now())
                .createdBy(userService.getCurrentUser())
                .build();
        return employeeMapper.toDTO(employeeRepository.save(employee));
    }


    //READ
    public List<EmployeeDTO> readAll() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> readById(Long id) {
        return employeeRepository.findById(id).map(employeeMapper::toDTO);
    }

    public Optional<EmployeeDTO> readByFullName(String firstName, String middleName, String lastName) {
        return employeeRepository.findByFirstNameAndMiddleNameAndLastName
                        (firstName, middleName, lastName)
                .map(employeeMapper::toDTO);
    }

    public List<EmployeeDTO> readManagers() {
        return employeeRepository.findManagers().stream()
                .map(employeeMapper::toDTO).collect(Collectors.toList());
    }

    //UPDATE
    @Transactional
    public EmployeeDTO update(Long id, EmployeeDTO updatedEmployeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        employeeMapper.updateModelFromDto(updatedEmployeeDTO, existingEmployee);
        existingEmployee.setUpdatedAt(LocalDateTime.now());
        existingEmployee.setUpdatedBy(userService.getCurrentUser());
        employeeRepository.save(existingEmployee);
        return employeeMapper.toDTO(existingEmployee);
    }

    //DELETE
    @Transactional
    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        employeeRepository.delete(employee);
    }


}
