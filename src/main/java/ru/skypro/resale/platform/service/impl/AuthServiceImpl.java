package ru.skypro.resale.platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.openapitools.model.NewPasswordDto;
import org.openapitools.model.RegisterReqDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.resale.platform.entity.User;
import ru.skypro.resale.platform.exception.UserNotFoundException;
import ru.skypro.resale.platform.mapper.UserMapper;
import ru.skypro.resale.platform.repository.UserRepository;
import ru.skypro.resale.platform.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    @Override
    public boolean login(String userName, String password) {
        var userDetails = userDetailsService.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterReqDto registerReq) {
        if (userRepository.findByEmail(registerReq.getUsername()).isPresent()) {
            return false;
        }
        registerReq.setPassword(encoder.encode(registerReq.getPassword()));
        userRepository.save(userMapper.registerReqDtoToUser(registerReq));
        return true;
    }

    @Override
    public boolean changePassword(NewPasswordDto newPassword, String userName) {
        User user = userRepository.findByEmail(userName).orElseThrow(UserNotFoundException::new);
        if (encoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(newPassword.getNewPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
