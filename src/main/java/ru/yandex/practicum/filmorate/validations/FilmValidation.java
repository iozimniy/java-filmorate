package ru.yandex.practicum.filmorate.validations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
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

        if (request.getMpa() != null && !ratingStorage.contains(request.getMpa().getId())) {
            log.warn("Не существует рейтинга с id {}", request.getMpa());
            throw new ValidationException("Не существует рейтинга с id " + request.getMpa());
        }

        if (request.getGenres() != null) {
            // Тут можно использовать запрос с EXIST, но тогда мы не получим несуществующий id
            List<Long> genresId = genreStorage.getAllId();
            request.getGenres().forEach(genre -> {
                if (!genresId.contains(genre.getId())) {
                    log.warn("Не существует жанра с id {}", genre.getId());
                    throw new ValidationException("Не существует жанра с id " + genre.getId());
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

        if (film.getMpa() != null && !ratingStorage.contains(film.getMpa().getId())) {
            log.warn("Не существует рейтинга с id {}", film.getMpa());
            throw new ValidationException("Не существует рейтинга с id " + film.getMpa());
        }
    }
}
