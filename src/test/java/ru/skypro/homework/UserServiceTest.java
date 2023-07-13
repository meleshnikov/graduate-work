package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.UserService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.skypro.homework.AdServiceTestHelp.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, imageRepository, passwordEncoder, userMapper);
    }

    @Test
    public void testLoadUserByUsernameReturnsUserDetails() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        user.setRole(Role.USER);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals(Role.USER.name(), userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    public void testCreateUserIsOk() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        userService.createUser(user);

        verify(userRepository).save(user);
        verify(passwordEncoder).encode("password");
    }

    @Test
    public void testGetCurrentUsernameReturnsUsername() {
        String username = "testuser";
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn(username);

        String currentUsername = userService.getCurrentUsername();

        assertEquals(username, currentUsername);
    }


    @Test
    public void testGetCurrentUserRoleReturnsRole() {
        String role = "ROLE_USER";
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        List<GrantedAuthority> authorities = Arrays.asList(authority);
        when(authentication.getAuthorities()).thenAnswer(invocation -> authorities);

        String currentUserRole = userService.getCurrentUserRole();

        assertEquals(role, currentUserRole);
    }
    @Test
    public void testSetPasswordSetNewPassword() {

        String newPassword = "newpassword";
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        User currentUser = Mockito.mock(User.class);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(currentUser));
        when(passwordEncoder.matches(PASSWORD, currentUser.getPassword())).thenReturn(true);
        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.setCurrentPassword(PASSWORD);
        newPasswordDto.setNewPassword(newPassword);
        UserService userService = new UserService(userRepository, null, passwordEncoder, null);

        boolean passwordChanged = userService.setPassword(newPasswordDto);

        assertTrue(passwordChanged);
        verify(passwordEncoder).encode(newPassword);
        verify(currentUser).setPassword(passwordEncoder.encode(newPassword));
        assertEquals(passwordEncoder.encode(newPassword), currentUser.getPassword());
    }

    @Test
    public void testGetUser() {

        User currentUser = Mockito.mock(User.class);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(currentUser));
        UserService userService = new UserService(userRepository, null, null, userMapper);
        UserDto userDto = userService.getUser();

        assertNotNull(userDto);
        verify(userMapper).toUserDto(any(UserDto.class), eq(currentUser));
    }

    @Test
    public void testUpdateUserOk() {
        UserDto userDto = new UserDto();
        Optional<User> currentUser = Optional.of(Mockito.mock(User.class));
        when(userRepository.findByUsername(USERNAME)).thenReturn(currentUser);
        UserService userService = new UserService(userRepository, null, null, userMapper);
        boolean updated = userService.updateUser(userDto);

        assertTrue(updated);
        verify(userMapper).toUser(any(User.class), eq(userDto));
    }

    @Test
    public void testUpdateUserNotExist() {
        UserDto userDto = new UserDto();
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        UserService userService = new UserService(userRepository, null, null, userMapper);
        boolean updated = userService.updateUser(userDto);

        assertFalse(updated);
    }

    @Test
    public void testGetUserImageOk() {
        Image image = Mockito.mock(Image.class);
        User user = Mockito.mock(User.class);
        when(userRepository.findById(TEST_ID)).thenReturn(Optional.of(user));
        when(user.getImage()).thenReturn(image);

        UserService userService = new UserService(userRepository, null, null, null);
        Image result = userService.getUserImage(TEST_ID);

        assertEquals(image, result);
    }

    @Test
    public void testUpdateUserImageOk() throws IOException {
        User user = Mockito.mock(User.class);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.getContentType()).thenReturn(FILE_CONTENT_TYPE);
        when(file.getBytes()).thenReturn(FILE_CONTENT);

        UserService userService = new UserService(userRepository, imageRepository, null, null);
        userService.updateUserImage(file);

        verify(imageRepository).save(any(Image.class));
        verify(user).setImage(any(Image.class));
    }

}