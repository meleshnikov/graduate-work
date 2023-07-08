package ru.skypro.resale.platform.service;

import org.openapitools.model.NewPasswordDto;
import org.openapitools.model.RegisterReqDto;
import org.openapitools.model.RegisterReqDto.RoleEnum;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReqDto registerReq);
    boolean changePassword(NewPasswordDto password, String userName);
}
