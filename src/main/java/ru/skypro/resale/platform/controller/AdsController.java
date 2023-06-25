package ru.skypro.resale.platform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    @Operation(
            operationId = "addAd",
            summary = "Добавить объявление",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAd(@Valid @RequestPart CreateAdsDto properties,
                                        @RequestPart MultipartFile image) {
        //TODO
        return null;
    }

    @Operation(
            operationId = "addComment",
            summary = "Добавить комментарий к объявлению",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id,
                                                 @RequestBody CommentDto commentDto) {
        //TODO
        return null;
    }

    @Operation(
            operationId = "deleteComment",
            summary = "Удалить комментарий",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("adId") Integer adId,
                                              @PathVariable("commentId") Integer commentId) {
        //TODO
        return null;
    }

    @Operation(
            operationId = "getAds",
            summary = "Получить информацию об объявлении",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = FullAdsDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getAds(@PathVariable Integer id) {
        //TODO
        return null;

    }

    @Operation(
            operationId = "getAdsMe",
            summary = "Получить объявления авторизованного пользователя",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperAdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe() {
        //TODO
        return null;

    }

    @Operation(
            operationId = "getAllAds",
            summary = "Получить все объявления",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperAdsDto.class))
                    })
            }
    )
    @GetMapping
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        //TODO
        return null;
    }

    @Operation(
            operationId = "getComments",
            summary = "Получить комментарии объявления",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperCommentDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperCommentDto> getComments(@PathVariable Integer id) {
        //TODO
        return null;
    }

    @Operation(
            operationId = "removeAd",
            summary = "Удалить объявление",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable Integer id) {
        //TODO
        return null;
    }

    @Operation(
            operationId = "updateAds",
            summary = "Обновить информацию об объявлении",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable Integer id,
                                            @Valid @RequestBody CreateAdsDto createAdsDto) {
        //TODO
        return null;
    }

    @Operation(
            operationId = "updateComment",
            summary = "Обновить комментарий",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("adId") Integer adId,
                                                    @PathVariable("commentId") Integer commentId,
                                                    @RequestBody CommentDto commentDto) {
        //TODO
        return null;
    }

    @Operation(
            operationId = "updateImage",
            summary = "Обновить картинку объявления",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(schema = @Schema(implementation = byte[].class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(value = "/{id}/image", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id,
                                              @RequestPart MultipartFile image) {
        //TODO
        return null;
    }
}
