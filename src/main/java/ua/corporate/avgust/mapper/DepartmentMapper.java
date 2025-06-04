package ua.corporate.avgust.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.corporate.avgust.dto.DepartmentDTO;
import ua.corporate.avgust.model.Department;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDTO toDTO(Department department);

    @Mapping(target = "id", ignore = true)
    Department toModel(DepartmentDTO departmentDTO);

    void updateModelFromDto(DepartmentDTO dto, @MappingTarget Department entity);
}
