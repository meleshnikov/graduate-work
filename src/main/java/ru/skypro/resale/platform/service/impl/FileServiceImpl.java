package ru.skypro.resale.platform.service.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.resale.platform.exception.FileNotSavedException;
import ru.skypro.resale.platform.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String saveFile(String name, String dir, MultipartFile file) {
        var extension = FilenameUtils.getExtension(file.getOriginalFilename());

        var filePath = Path.of(dir, String.join(".", name, extension));

        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            Files.write(filePath, file.getBytes());
        } catch (IOException ex) {
            throw new FileNotSavedException("Не удалось сохранить файл");
        }
        return filePath.toString();
    }
}
