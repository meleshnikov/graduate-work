package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.service.UserService;


import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
@Tag(name = "Users")
public class UserController {

    private final UserService service;

    @Operation(
            summary = "Change password",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Password changed successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = NewPasswordDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error when changing password",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = NewPasswordDto.class)
                            )
                    )
            }
    )
    @PostMapping("/set_password")
    public ResponseEntity<?> changePassword(@RequestBody NewPasswordDto newPassword, Authentication authentication) {
        service.changePassword(newPassword, authentication);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get an authorized user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authorized user has been received",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Authorized user has not been received",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            }
    )
    @GetMapping("/me")
    public ResponseEntity<UserDto> getAuthorizedUser(Authentication authentication) {
        return ResponseEntity.ok(service.getAuthorizedUserDto(authentication));
    }

    @Operation(
            summary = "Update user information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User data updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "An error occurred while updating data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            }
    )
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, Authentication authentication) {
        return ResponseEntity.ok( service.updateUser(userDto, authentication));
    }

    @Operation(
            summary = "User avatar update",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User avatar updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Image.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User avatar update error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Image.class)
                            )
                    )
            }
    )
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatar(@RequestPart MultipartFile image, Authentication authentication) throws IOException {
        service.updateAvatar(image, authentication);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Getting a user's avatar",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User avatar received successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Image.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User avatar getting error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Image.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable long id) throws IOException {
        log.info("Get user image with id" + id);
        return ResponseEntity.ok(service.getUserImage(id));
    }
}