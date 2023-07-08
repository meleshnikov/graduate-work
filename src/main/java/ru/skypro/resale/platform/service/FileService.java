package ru.skypro.resale.platform.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveFile(String name, String dir, MultipartFile file);
}
