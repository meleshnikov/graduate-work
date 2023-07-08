package ru.skypro.resale.platform.service.impl;

import lombok.RequiredArgsConstructor;
import org.openapitools.model.NewPasswordDto;
import org.openapitools.model.RegisterReqDto;
import org.openapitools.model.RegisterReqDto.RoleEnum;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.resale.platform.mapper.UserMapper;
import ru.skypro.resale.platform.repository.UserRepository;
import ru.skypro.resale.platform.service.AuthService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UserDetailsManager manager;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        var userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterReqDto registerReq) {
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(Optional.ofNullable(registerReq.getRole()).map(RoleEnum::name).orElse(RoleEnum.USER.name()))
                        .build());
        userRepository.save(userMapper.registerReqDtoToUser(registerReq));
        return true;
    }

    @Override
    public boolean changePassword(NewPasswordDto password, String userName) {
        if (login(userName, password.getCurrentPassword())) {
            manager.changePassword(password.getCurrentPassword(), "{bcrypt}" + encoder.encode(password.getNewPassword()));
            return true;
        }
        return false;
    }
}
