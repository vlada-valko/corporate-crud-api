package ua.corporate.avgust.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.corporate.avgust.dto.PositionDTO;
import ua.corporate.avgust.mapper.PositionMapper;
import ua.corporate.avgust.model.Department;
import ua.corporate.avgust.model.Position;
import ua.corporate.avgust.repository.PositionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final UserService userService;


    //CREATE
    @Transactional
    public PositionDTO create(PositionDTO positionDTO) {
        Position position = positionMapper.toModel(positionDTO)
                .toBuilder()
                .createdAt(LocalDateTime.now())
                .createdBy(userService.getCurrentUser())
                .build();
        return positionMapper.toDTO(positionRepository.save(position));
    }

    //READ
    public List<PositionDTO> readAll() {
        return positionRepository.findAll().stream()
                .map(positionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PositionDTO> readById(Long id) {
        return positionRepository.findById(id)
                .map(positionMapper::toDTO);
    }

    public Optional<PositionDTO> readByName(String name) {
        return positionRepository.findByName(name).map(positionMapper::toDTO);
    }

    //UPDATE
    @Transactional
    public PositionDTO update(Long id, PositionDTO updatedPositionDTO) {
        Position position = positionMapper.toModel(updatedPositionDTO)
                .toBuilder()
                .id(id)
                .updatedAt(LocalDateTime.now())
                .updatedBy(userService.getCurrentUser())
                .build();
        return positionMapper.toDTO(positionRepository.save(position));
    }

    //DELETE
    @Transactional
    public void delete(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Position not found"));
        positionRepository.delete(position);
    }
}
