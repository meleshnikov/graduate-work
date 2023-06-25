package ru.skypro.resale.platform.mapper;

import org.mapstruct.Mapper;
import org.openapitools.model.UserDto;
import ru.skypro.resale.platform.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto entityToDto(User source);
}
