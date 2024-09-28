package ru.yandex.practicum.filmorate.validations;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidation {
    public static void validate(User user) throws ValidationException {
        if (user.getEmail().isBlank() || !(user.getEmail().contains("@"))) {
            throw new ValidationException("Некорректный адрес элекронной почты");
        }

        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен быть пустым и не должен содержат пробелы");
        }

        if (user.getBirthday().isAfter(LocalDate.now().atStartOfDay())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }
}
