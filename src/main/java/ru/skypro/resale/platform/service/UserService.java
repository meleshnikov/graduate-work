package ru.skypro.resale.platform.service;

import org.openapitools.model.UserDto;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.resale.platform.entity.User;

public interface UserService {

    User getUserByEmail(String email);

    UserDto getUser(String username);

    UserDto updateUser(UserDto user, String username);

    void updateAvatar(MultipartFile image, String username);

    byte[] getAvatarAsBytes(String fileName);
}
