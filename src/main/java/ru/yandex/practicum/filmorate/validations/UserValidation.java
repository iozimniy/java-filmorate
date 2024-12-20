package ru.yandex.practicum.filmorate.validations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
@Component
public class UserValidation {
    public void validateForCreate(User user) throws ValidationException {
        if (!(StringUtils.hasText(user.getEmail()))) {
            log.warn("Некорректный адрес элекронной почты");
            throw new ValidationException("Некорректный адрес элекронной почты");
        }

        if (!(StringUtils.hasText(user.getLogin())) || user.getLogin().contains(" ")) {
            log.warn("Логин не должен быть пустым и не должен содержат пробелы");
            throw new ValidationException("Логин не должен быть пустым и не должен содержат пробелы");
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Дата рождения не может быть в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }

    public void validateForUpdate(User user) {
        if (user.getId() == null) {
            log.warn("Не указан пользователь для изменения");
            throw new ValidationException("Не указан пользователь для изменения");
        }

        if (user.getLogin() != null && (user.getLogin().isBlank() || user.getLogin().contains(" "))) {
            log.warn("Логин не должен быть пустым и не должен содержат пробелы");
            throw new ValidationException("Логин не должен быть пустым и не должен содержат пробелы");
        }

        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Дата рождения не может быть в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}
