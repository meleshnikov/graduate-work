package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;

import java.io.IOException;

public interface ImageService {
    Image downloadImage(MultipartFile image) throws IOException;
    void deleteImage(Long id);
    byte[] getImage(Long id);
}
