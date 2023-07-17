package ru.skypro.resale.platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.openapitools.model.RegisterReqDto;
import org.openapitools.model.UserDto;
import ru.skypro.resale.platform.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User source);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "image",ignore = true)
    @Mapping(target = "email",ignore = true)
    void updateUser(@MappingTarget User target, UserDto source);

    @Mapping(target = "email", source = "username")
    @Mapping(target = "role", source = "role", defaultValue = "USER")
    User registerReqDtoToUser(RegisterReqDto registerReqDto);
}
