package ru.skypro.resale.platform.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.LoginReqDto;
import org.openapitools.model.RegisterReqDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.resale.platform.service.AuthService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDto req) {
        return authService.login(req.getUsername(), req.getPassword()) ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReqDto req) {
        return authService.register(req) ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
