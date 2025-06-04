package ua.corporate.avgust.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.corporate.avgust.dto.WorkplaceTypeDTO;
import ua.corporate.avgust.model.WorkplaceType;

@Mapper(componentModel = "spring")
public interface WorkplaceTypeMapper {

    WorkplaceTypeDTO toDTO(WorkplaceType workplaceType);

    @Mapping(target = "id", ignore = true)
    WorkplaceType toModel(WorkplaceTypeDTO workplaceTypeDTO);

    void updateModelFromDto(WorkplaceTypeDTO dto, @MappingTarget WorkplaceType entity);
}
