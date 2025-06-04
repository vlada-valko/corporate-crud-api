package ua.corporate.avgust.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.corporate.avgust.dto.PositionDTO;
import ua.corporate.avgust.model.Position;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    PositionDTO toDTO(Position position);

    @Mapping(target = "id", ignore = true)
    Position toModel(PositionDTO positionDTO);

    void updateModelFromDto(PositionDTO dto, @MappingTarget Position entity);
}
