package ru.skypro.resale.platform.service;

import org.openapitools.model.NewPasswordDto;
import org.openapitools.model.UserDto;

public interface UserService {

    UserDto getUser(String email);
    UserDto updateUser(UserDto user, String email);
}
