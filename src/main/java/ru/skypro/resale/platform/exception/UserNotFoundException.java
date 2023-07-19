package ru.skypro.resale.platform.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(code = BAD_REQUEST)
public class UserNotFoundException extends EntityNotFoundException {
}
