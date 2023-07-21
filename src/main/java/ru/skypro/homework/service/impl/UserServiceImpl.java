package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.Exceptions.UserNotFoundException;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;
    private final PasswordEncoder encoder;

    @Override
    public UserDto updateUser(UserDto userDTO, Authentication authentication) {
        log.info("Request to update user");
        User user = userRepository.findByEmailIgnoreCase(authentication.getName()).orElseThrow(UserNotFoundException::new);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDto getAuthorizedUserDto(Authentication authentication) {
        log.info("Request to getting authorized user");
        return userMapper.toDTO(userRepository.getUserByEmail(authentication.getName()));
    }

    @Override
    public User getAuthorizedUser(Authentication authentication) {
        log.info("Request to getting authorized user");
        return userRepository.findByEmailIgnoreCase(authentication.getName()).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void changePassword(NewPasswordDto newPasswordDto, Authentication authentication) {
        log.info("Request to change password");
        User user = userRepository.findByEmailIgnoreCase(authentication.getName()).orElseThrow(UserNotFoundException::new);
        user.setPassword(encoder.encode(newPasswordDto.getNewPassword()));
        userRepository.save(user);
        userMapper.toDTO(user);
    }

    @Transactional
    @Override
    public void updateAvatar(MultipartFile image, Authentication authentication) throws IOException {
        log.info("Request to update avatar of user");
        User user = userRepository.findByEmailIgnoreCase(authentication.getName()).orElseThrow(UserNotFoundException::new);
        user.setImage(imageService.downloadImage(image));
        userRepository.save(user);
        userMapper.toDTO(user);
    }

    @Transactional
    @Override
    public byte[] getUserImage(Long userId) throws IOException {
        log.info("Request to getting image");
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (user.getImage() != null) {
            return user.getImage().getData();
        } else {
            File emptyAvatar = new File("src/main/resources/static/emptyAvatar.png");
            return Files.readAllBytes(emptyAvatar.toPath());
        }
    }
}