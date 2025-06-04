package ua.corporate.avgust.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.corporate.avgust.dto.UserDTO;
import ua.corporate.avgust.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    User toModel(UserDTO userDTO);

    void updateModelFromDto(UserDTO dto, @MappingTarget User entity);
}
