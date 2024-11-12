package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.ErrorResponse;
import ru.yandex.practicum.filmorate.exceptions.InternalServerException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.info("Отправлен ответ на исключение NotFoundException: {}", e.getMessage());
        return new ErrorResponse("Ошибка запроса", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.info("Отправлен ответ на исключение ValidationException: {}", e.getMessage());
        return new ErrorResponse("Ошибка при валидации запроса", e.getMessage());
    }

    public ErrorResponse handleInternalServerException(final InternalServerException e) {
        log.info("Отправлен ответ на исключение InternalServerException: {}", e.getMessage());
        return new ErrorResponse("Не удалось обработать запрос", e.getMessage());
    }
}
