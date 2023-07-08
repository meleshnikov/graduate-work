package ru.skypro.resale.platform.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.LoginReqDto;
import org.openapitools.model.RegisterReqDto;
import org.springframework.http.HttpStatus;
import ru.skypro.resale.platform.service.AuthService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    public void successLogin_shouldReturnStatusOk() {
        var requestBody = new LoginReqDto();
        when(authService.login(any(), any())).thenReturn(true);

        var result = authController.login(requestBody);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void failLogin_shouldReturnStatusForbidden() {
        var requestBody = new LoginReqDto();
        when(authService.login(any(), any())).thenReturn(false);

        var result = authController.login(requestBody);

        assertNotNull(result);
        assertEquals(FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void successRegister_shouldReturnStatusOk() {
        var requestBody = new RegisterReqDto();
        when(authService.register(any())).thenReturn(true);

        var result = authController.register(requestBody);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void failRegister_shouldReturnStatusBadRequest() {
        var requestBody = new RegisterReqDto();
        when(authService.register(any())).thenReturn(false);

        var result = authController.register(requestBody);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}