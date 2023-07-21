package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.LoginReqDto;
import ru.skypro.homework.dto.RegisterReqDto;
import ru.skypro.homework.dto.SecurityUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(target = "image", source = "image")
    User toEntity(UserDto dto);

    @Mapping(target = "image",expression = "java(imageMapper(entity))")
    UserDto toDTO(User entity);

    default String imageMapper(User user){
        return "/users/"+ user.getId() + "/image";
    }

    Image map(String value);

    User toEntity(LoginReqDto dto);

    @Mapping(target = "email", source = "username")
    User toEntity(RegisterReqDto req);

    SecurityUserDto toSecurityDto(User user);
}