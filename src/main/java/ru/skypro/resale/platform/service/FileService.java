package ru.skypro.resale.platform.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileService {

    String saveFile(String name, String dir, MultipartFile file);

    void removeFile(String path);

    byte[] getBytes(Path path);
}
