package ua.corporate.avgust.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;
import ua.corporate.avgust.dto.EmployeeDTO;
import ua.corporate.avgust.model.Employee;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDTO toDTO(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Employee toModel(EmployeeDTO employeeDTO);

    void updateModelFromDto(EmployeeDTO dto, @MappingTarget Employee entity);

}
