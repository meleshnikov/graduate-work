package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.service.impl.UserService;

import java.io.IOException;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Обновление пароля",
            tags = "Пользователи",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NewPasswordDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            })
    @Secured("USER")
    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDto newPassword) {

        if (userService.setPassword(newPassword)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }


    @Operation(summary = "Получить информацию об авторизованном пользователе",
            tags = "Пользователи",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            })
    @Secured("USER")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

    @Operation(summary = "Обновить информацию об авторизованном пользователе",
            tags = "Пользователи",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            })
    @Secured("USER")
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        if (userService.updateUser(user)) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Operation(summary = "Обновить аватар авторизованного пользователя",
            tags = "Пользователи",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema()
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            })
    @Secured("USER")
    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestParam("image") MultipartFile file) throws IOException {

        userService.updateUserImage(file);
        return ResponseEntity.ok().build();
    }

    @Secured("USER")
    @GetMapping("/image/{id}/from-db")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Integer id) {
        Image image = userService.getUserImage(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getMediaType()));
        headers.setContentLength(image.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(image.getData());
    }

}

