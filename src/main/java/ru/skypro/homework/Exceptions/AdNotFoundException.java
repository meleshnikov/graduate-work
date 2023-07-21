package ru.skypro.homework.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdNotFoundException extends RuntimeException{

    public AdNotFoundException(){super("Not found AD in database");}
}
