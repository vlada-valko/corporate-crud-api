package ua.corporate.avgust.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.corporate.avgust.dto.DepartmentDTO;
import ua.corporate.avgust.mapper.DepartmentMapper;
import ua.corporate.avgust.model.Department;
import ua.corporate.avgust.repository.DepartmentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class DepartmentService {
    //region standard fields
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final UserService userService;
    //endregion

    //CREATE
    @Transactional
    public DepartmentDTO create(DepartmentDTO departmentDTO) {
        Department department = departmentMapper.toModel(departmentDTO)
                .toBuilder()
                .createdAt(LocalDateTime.now())
                .createdBy(userService.getCurrentUser())
                .build();
        return departmentMapper.toDTO(departmentRepository.save(department));
    }

    //READ
    public List<DepartmentDTO> readAll() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<DepartmentDTO> readById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDTO);
    }

    public Optional<DepartmentDTO> readByName(String name) {
        return departmentRepository.findByName(name).map(departmentMapper::toDTO);
    }

    //UPDATE
    @Transactional
    public DepartmentDTO update(Long id, DepartmentDTO updatedDepartmentDTO) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        departmentMapper.updateModelFromDto(updatedDepartmentDTO, existingDepartment);
        existingDepartment.setUpdatedAt(LocalDateTime.now());
        existingDepartment.setUpdatedBy(userService.getCurrentUser());
        return departmentMapper.toDTO(departmentRepository.save(existingDepartment)
        );
    }

    //DELETE
    @Transactional
    public void delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        departmentRepository.delete(department);
    }

}
