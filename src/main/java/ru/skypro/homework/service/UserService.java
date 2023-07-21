package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

import java.io.IOException;

public interface UserService {

    UserDto updateUser(UserDto userDTO, Authentication authentication);
    UserDto getAuthorizedUserDto(Authentication authentication);
    User getAuthorizedUser(Authentication authentication);
    void changePassword(NewPasswordDto newPasswordDTO, Authentication authentication);
    void updateAvatar(MultipartFile image, Authentication authentication) throws IOException;
    byte[] getUserImage(Long userId) throws IOException;
}
