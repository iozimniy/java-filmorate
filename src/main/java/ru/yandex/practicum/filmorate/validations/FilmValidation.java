package ru.yandex.practicum.filmorate.validations;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
public class FilmValidation {

    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 21, 28);
    public static void validate(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания фильма 200 символов");
        }

        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE.atStartOfDay())) {
            throw new ValidationException("Дата релиза фильма не может быть раньше 28 декабря 1895 года");
        }

        if (film.getDuration().isNegative() || film.getDuration().isZero()) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
    }
}
