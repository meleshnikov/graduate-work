package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.RegisterReqDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserService manager;

  private final PasswordEncoder encoder;
  private final UserMapper userMapper;

  public AuthServiceImpl(UserService manager, PasswordEncoder encoder, UserMapper userMapper) {
    this.manager = manager;
    this.encoder = encoder;
    this.userMapper = userMapper;
  }

  @Override
  @Transactional
  public boolean login(String userName, String password) {
    if (!manager.userExists(userName)) {
      return false;
    }
    UserDetails userDetails = manager.loadUserByUsername(userName);
    return encoder.matches(password, userDetails.getPassword());
  }

  @Override
  public boolean register(RegisterReqDto registerReq, Role role) {
    if (manager.userExists(registerReq.getUsername())) {
      return false;
    }
    registerReq.setRole(role);
    manager.createUser(userMapper.toUser(registerReq));
        User.builder()
            .passwordEncoder(this.encoder::encode)
            .password(registerReq.getPassword())
            .username(registerReq.getUsername())
            .roles(role.name())
            .build();

    return true;
  }
}
