package ru.skypro.resale.platform.exception;

public class FileNotSavedException extends RuntimeException {
    public FileNotSavedException(String message) {
        super(message);
    }
}
