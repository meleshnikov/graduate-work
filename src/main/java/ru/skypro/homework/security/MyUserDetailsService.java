package ru.skypro.homework.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.SecurityUserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MyUserDetails userDetails;

    /**
     * The method loads the user by username
     *
     * @param username the username identifying the user whose data is required.
     * @return displays information about the user
     * @throws UsernameNotFoundException Exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUserDto securityUserDto = userRepository.findByEmailIgnoreCase(username)
                .map(userMapper::toSecurityDto)
                .orElseThrow(() -> new UsernameNotFoundException("no username"));
        userDetails.setUserDto(securityUserDto);
        return userDetails;
    }
}