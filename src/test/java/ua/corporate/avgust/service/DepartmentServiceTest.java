package ua.corporate.avgust.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import ua.corporate.avgust.dto.DepartmentDTO;
import ua.corporate.avgust.mapper.DepartmentMapper;
import ua.corporate.avgust.model.Department;
import ua.corporate.avgust.model.User;
import ua.corporate.avgust.repository.DepartmentRepository;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentMapper departmentMapper;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void testCreateDepartment() {
        // Данні для тесту
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName("Test Department");

        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setUsername("testuser");

        Department departmentFromMapper = Department.builder()
                .name(dto.getName())
                .build();

        Department savedDepartment = Department.builder()
                .id(1L)
                .name(dto.getName())
                .createdAt(LocalDateTime.now())
                .createdBy(currentUser)
                .build();

        DepartmentDTO returnedDTO = new DepartmentDTO();
        returnedDTO.setName(dto.getName());
        returnedDTO.setId(1L);

        when(departmentMapper.toModel(dto)).thenReturn(departmentFromMapper);
        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(departmentRepository.save(any(Department.class))).thenReturn(savedDepartment);
        when(departmentMapper.toDTO(savedDepartment)).thenReturn(returnedDTO);

        DepartmentDTO result = departmentService.create(dto);

        assertNotNull(result);
        assertEquals("Test Department", result.getName());
        assertEquals(1L, result.getId());
    }
}
