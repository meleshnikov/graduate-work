package ru.skypro.resale.platform.service.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.resale.platform.exception.FileNotRemovedException;
import ru.skypro.resale.platform.exception.FileNotSavedException;
import ru.skypro.resale.platform.exception.FileReadBytesException;
import ru.skypro.resale.platform.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String saveFile(String name, String dir, MultipartFile file) {
        var extension = FilenameUtils.getExtension(file.getOriginalFilename());
        var nameWithExtension = String.join(".", name, extension);
        var filePath = Path.of(dir, nameWithExtension);

        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            Files.write(filePath, file.getBytes());
        } catch (IOException ex) {
            throw new FileNotSavedException("Не удалось сохранить файл");
        }
        return String.format("/%s/%s", dir, nameWithExtension);
    }

    @Override
    public void removeFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new FileNotRemovedException("Не удалось удалить файл");
        }
    }

    @Override
    public byte[] getBytes(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new FileReadBytesException("Возникла ошибка при чтении файла");
        }
    }
}
