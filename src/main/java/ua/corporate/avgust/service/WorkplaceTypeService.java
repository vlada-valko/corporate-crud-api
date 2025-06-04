package ua.corporate.avgust.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.corporate.avgust.dto.WorkplaceTypeDTO;
import ua.corporate.avgust.mapper.WorkplaceTypeMapper;
import ua.corporate.avgust.model.WorkplaceType;
import ua.corporate.avgust.repository.WorkplaceTypeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class WorkplaceTypeService {
    //region standard fields
    private final WorkplaceTypeRepository workplaceTypeRepository;
    private final WorkplaceTypeMapper workplaceTypeMapper;
    private final UserService userService;
    //endregion

    //CREATE
    @Transactional
    public WorkplaceTypeDTO create(WorkplaceTypeDTO workplaceTypeDTO) {
        WorkplaceType workplaceType = workplaceTypeMapper.toModel(workplaceTypeDTO)
                .toBuilder()
                .createdAt(LocalDateTime.now())
                .createdBy(userService.getCurrentUser())
                .build();
        return workplaceTypeMapper.toDTO(workplaceTypeRepository.save(workplaceType));
    }

    //READ
    public List<WorkplaceTypeDTO> readAll() {
        return workplaceTypeRepository.findAll().stream()
                .map(workplaceTypeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<WorkplaceTypeDTO> readById(Long id) {
        return workplaceTypeRepository.findById(id)
                .map(workplaceTypeMapper::toDTO);
    }

    public Optional<WorkplaceTypeDTO> readByName(String name) {
        return workplaceTypeRepository.findByName(name)
                .map(workplaceTypeMapper::toDTO);
    }

    //UPDATE
    @Transactional
    public WorkplaceTypeDTO update(Long id, WorkplaceTypeDTO updatedWorkplaceTypeDTO) {
        WorkplaceType existingWorkplaceType = workplaceTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workplace type not found"));
        workplaceTypeMapper.updateModelFromDto(updatedWorkplaceTypeDTO, existingWorkplaceType);
        existingWorkplaceType.setUpdatedAt(LocalDateTime.now());
        existingWorkplaceType.setUpdatedBy(userService.getCurrentUser());
        return workplaceTypeMapper.toDTO(workplaceTypeRepository.save(existingWorkplaceType));
    }

    //DELETE
    @Transactional
    public void delete(Long id) {
        WorkplaceType workplaceType = workplaceTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workplace type not found"));
        workplaceTypeRepository.delete(workplaceType);
    }
}
