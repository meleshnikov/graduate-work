package ru.skypro.resale.platform.service;

import org.openapitools.model.NewPasswordDto;
import ru.skypro.resale.platform.dto.RegisterReq;
import ru.skypro.resale.platform.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
    boolean changePassword(NewPasswordDto password, String userName);
}
