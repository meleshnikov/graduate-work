package ru.skypro.resale.platform.service;

import org.openapitools.model.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserDto getUser(String email);
    UserDto updateUser(UserDto user, String email);

    void updateAvatar(MultipartFile image, String name);
}
