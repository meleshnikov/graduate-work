package ru.skypro.resale.platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.openapitools.model.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.resale.platform.entity.User;
import ru.skypro.resale.platform.exception.UserNotFoundException;
import ru.skypro.resale.platform.mapper.UserMapper;
import ru.skypro.resale.platform.repository.UserRepository;
import ru.skypro.resale.platform.service.FileService;
import ru.skypro.resale.platform.service.UserService;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileService fileService;

    @Value("${users.avatars.dir}")
    private String avatarsDirPath;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDto getUser(String username) {
        return userMapper.toUserDto(getUserByEmail(username));
    }

    @Override
    public UserDto updateUser(UserDto source, String username) {
        var user = getUserByEmail(username);
        userMapper.updateUser(user, source);
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public void updateAvatar(MultipartFile image, String username) {
        var user = getUserByEmail(username);
        user.setImage(fileService.saveFile(user.getId().toString(), avatarsDirPath, image));
        userRepository.save(user);
    }

    @Override
    public byte[] getAvatarAsBytes(String fileName) {
        return fileService.getBytes(Path.of(avatarsDirPath, fileName));
    }
}
