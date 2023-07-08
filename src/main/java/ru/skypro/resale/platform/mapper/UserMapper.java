package ru.skypro.resale.platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.openapitools.model.RegisterReqDto;
import org.openapitools.model.UserDto;
import ru.skypro.resale.platform.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userEntityToUserDto(User source);

    @Mapping(target = "avatarPath",ignore = true)
    void updateUserEntity(UserDto userDto, @MappingTarget User user);

    @Mapping(source = "username", target = "email")
    User registerReqDtoToUser(RegisterReqDto registerReqDto);
}
