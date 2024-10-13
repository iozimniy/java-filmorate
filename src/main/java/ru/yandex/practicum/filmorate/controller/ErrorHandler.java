package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.ErrorResponse;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
      log.info("Отправлен ответ на сключения NotFoundException: {}", e.getMessage());
      return new ErrorResponse("Ошибка запроса", e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.info("Отправлен ответ на сключения ValidationException: {}", e.getMessage());
        return new ErrorResponse("Ошибка при валидации запроса", e.getMessage());
    }
}
