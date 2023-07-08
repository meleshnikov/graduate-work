package ru.skypro.resale.platform.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.openapitools.model.NewPasswordDto;
import org.openapitools.model.RegisterReqDto;
import org.openapitools.model.RegisterReqDto.RoleEnum;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import ru.skypro.resale.platform.mapper.UserMapper;
import ru.skypro.resale.platform.repository.UserRepository;
import ru.skypro.resale.platform.service.AuthService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthServiceImplTest {

    private static final String USER_NAME = "user@gmail.com";
    private static final String USER_NAME_NEW = "user1@gmail.com";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_NEW = "password1";
    private static final String ROLE = "USER";

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private UserDetailsManager manager;

    private AuthService authService;

    @BeforeEach
    public void setUp() {
        var encoder = new BCryptPasswordEncoder();
        this.manager = new InMemoryUserDetailsManager(User.builder()
                .username(USER_NAME)
                .password(PASSWORD)
                .passwordEncoder(encoder::encode)
                .roles(ROLE)
                .build());
        this.authService = new AuthServiceImpl(encoder, manager, userMapper, userRepository);
    }

    @Test
    public void loginAuthenticatedUser_shouldReturnTrue() {
        var result = authService.login(USER_NAME, PASSWORD);
        assertTrue(result);
    }

    @Test
    public void loginNotAuthenticatedUser_shouldReturnFalse() {
        var result = authService.login("user", "123");
        assertFalse(result);
    }

    @Test
    public void registerNewUser_shouldReturnTrue() {
        var registerReq = new RegisterReqDto()
                .username(USER_NAME_NEW)
                .password(PASSWORD_NEW)
                .role(RoleEnum.USER);

        assertFalse(manager.userExists(USER_NAME_NEW));
        var result = authService.register(registerReq);

        assertTrue(result);
        assertTrue(manager.userExists(USER_NAME_NEW));
    }

    @Test
    public void registerExistingUser_shouldReturnFalse() {
        var registerReq = new RegisterReqDto()
                .username(USER_NAME)
                .password(PASSWORD)
                .role(RoleEnum.USER);

        assertTrue(manager.userExists(USER_NAME));
        var result = authService.register(registerReq);

        assertFalse(result);
    }

//    @Test
//    public void changePasswordAuthenticatedUser_shouldReturnTrue() {
//        var newPasswordDto = new NewPasswordDto()
//                .currentPassword(PASSWORD)
//                .newPassword(PASSWORD_NEW);
//
//        var result = authService.changePassword(newPasswordDto, USER_NAME);
//        assertTrue(result);
//    }

}