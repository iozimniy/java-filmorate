package ru.yandex.practicum.filmorate.validations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.dal.RatingRepository;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class FilmValidation {

    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    private RatingStorage ratingStorage;
    private GenreStorage genreStorage;

    public void validateForCreate(NewFilmRequest request) {
        if (!(StringUtils.hasText(request.getName()))) {
            log.warn("Название фильма не может быть пустым");
            throw new ValidationException("Название фильма не может быть пустым");
        }

        if (request.getDescription().length() > 200) {
            log.warn("Максимальная длина описания фильма 200 символов");
            throw new ValidationException("Максимальная длина описания фильма 200 символов");
        }

        if (request.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            log.warn("Дата релиза фильма не может быть раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза фильма не может быть раньше 28 декабря 1895 года");
        }

        if (request.getDuration() <= 0) {
            log.warn("Продолжительность фильма должна быть положительным числом");
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }

        if (request.getRatingId() != null && !ratingStorage.isContainsId(request.getRatingId())) {
            log.warn("Не существует рейтинга с id {}", request.getRatingId());
            throw new ValidationException("Не существует рейтинга с id " + request.getRatingId());
        }

        if (request.getGenresId() != null) {
            request.getGenresId().stream().forEach(id ->
            {
                if (!genreStorage.isContainsId(id)) {
                    log.warn("Не существует жанра с id {}", id);
                    throw new ValidationException("Не существует жанра с id " + id);
                }
            });
        }
    }

    public void validateForUpdate(Film film) {
        if (film.getId() == null) {
            log.warn("Не указан фильм для изменения");
            throw new ValidationException("Не указан фильм для изменения");
        }

        if (film.getDescription() != null && film.getDescription().length() > 200) {
            log.warn("Максимальная длина описания фильма 200 символов");
            throw new ValidationException("Максимальная длина описания фильма 200 символов");
        }

        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            log.warn("Дата релиза фильма не может быть раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза фильма не может быть раньше 28 декабря 1895 года");
        }

        if ((film.getDuration() != null) && (film.getDuration() <= 0)) {
            log.warn("Продолжительность фильма должна быть положительным числом");
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }

        if (film.getRatingId() != null && !ratingStorage.isContainsId(film.getRatingId())) {
            log.warn("Не существует рейтинга с id {}", film.getRatingId());
            throw new ValidationException("Не существует рейтинга с id " + film.getRatingId());
        }
    }
}
