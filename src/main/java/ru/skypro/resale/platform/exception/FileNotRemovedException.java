package ru.skypro.resale.platform.exception;

public class FileNotRemovedException extends RuntimeException{
    public FileNotRemovedException(String message) {
        super(message);
    }
}
