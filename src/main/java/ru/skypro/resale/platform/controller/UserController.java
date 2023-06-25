package ru.skypro.resale.platform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.NewPasswordDto;
import org.openapitools.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.resale.platform.service.AuthService;
import ru.skypro.resale.platform.service.UserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    @Operation(
            operationId = "setPassword",
            summary = "Обновление пароля",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDto password, Authentication authentication) {
        return authService.changePassword(password, authentication.getName()) ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(
            operationId = "getUser",
            summary = "Получить информацию об авторизованном пользователе",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @Operation(
            operationId = "updateUser",
            summary = "Обновить информацию об авторизованном пользователе",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class))
                    }),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user, Authentication authentication) {
        return ResponseEntity.ok(userService.updateUser(user, authentication.getName()));
    }

    @Operation(
            operationId = "updateUserImage",
            summary = "Обновить аватар авторизованного пользователя",
            tags = { "Пользователи" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(value = "/me/image", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestPart(value = "image") MultipartFile image) {
        //TODO
        return null;
    }
}
