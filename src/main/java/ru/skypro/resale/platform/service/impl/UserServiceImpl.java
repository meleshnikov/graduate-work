package ru.skypro.resale.platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.openapitools.model.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.resale.platform.exception.UserNotFoundException;
import ru.skypro.resale.platform.mapper.UserMapper;
import ru.skypro.resale.platform.repository.UserRepository;
import ru.skypro.resale.platform.service.FileService;
import ru.skypro.resale.platform.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileService fileService;

    @Value("${users.avatars.dir}")
    private String avatarsDirPath;

    @Override
    public UserDto getUser(String email) {
        var foundUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return userMapper.userEntityToUserDto(foundUser);
    }

    @Override
    public UserDto updateUser(UserDto user, String email) {
        var foundUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        userMapper.updateUserEntity(user, foundUser);
        return userMapper.userEntityToUserDto(userRepository.save(foundUser));
    }

    @Override
    public void updateAvatar(MultipartFile image, String name) {
        var user = userRepository.findByEmail(name).orElseThrow(UserNotFoundException::new);
        user.setAvatarPath(fileService.saveFile(name, avatarsDirPath, image));
        userRepository.save(user);
    }
}
