package ru.skypro.resale.platform.service;

import org.openapitools.model.NewPasswordDto;
import org.openapitools.model.RegisterReqDto;

public interface AuthService {
    boolean login(String username, String password);
    boolean register(RegisterReqDto registerReq);
    boolean changePassword(NewPasswordDto newPassword, String username);
}
